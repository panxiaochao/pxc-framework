package io.github.panxiaochao.plugin.loader;

import io.github.panxiaochao.common.utils.SpringContextUtil;
import io.github.panxiaochao.plugin.api.IPlugin;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * {@code PluginLoader}
 * <p> description: Plugin loader
 *
 * @author Lypxc
 * @since 2023-04-10
 */
public class PluginLoader extends ClassLoader implements Closeable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    static {
        ClassLoader.registerAsParallelCapable();
    }

    private static volatile PluginLoader pluginLoader;

    private final ReentrantLock lock = new ReentrantLock();

    private final List<PluginJar> jars = new ArrayList<>();

    private final Set<String> names = new HashSet<>();

    private final Map<String, Class<?>> classCache = new ConcurrentHashMap<>();

    /**
     * <p>Get plugin loader instance.
     * <p>饿汉模式，多线程安全
     *
     * @return plugin loader instance
     */
    public static PluginLoader getInstance() {
        if (null == pluginLoader) {
            synchronized (PluginLoader.class) {
                if (null == pluginLoader) {
                    pluginLoader = new PluginLoader();
                }
            }
        }
        return pluginLoader;
    }

    /**
     * Load extend plugins list.
     *
     * @param path the path
     * @return the list
     */
    public List<PluginLoaderResult> loadExtendPlugins(final String path) {
        File[] jarFiles = PluginPathParser.getPluginFile(path).listFiles(file -> file.getName().endsWith(".jar"));
        if (Objects.isNull(jarFiles)) {
            return Collections.emptyList();
        }
        List<PluginLoaderResult> results = new ArrayList<>();
        try {
            for (File each : jarFiles) {
                JarFile jar = new JarFile(each, true);
                jars.add(new PluginJar(jar, each));
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    String entryName = jarEntry.getName();
                    if (entryName.endsWith(".class") && !entryName.contains("$")) {
                        String className = entryName.substring(0, entryName.length() - 6).replaceAll("/", ".");
                        names.add(className);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load JarFile", e);
        }
        names.forEach(className -> {
            Object instance;
            try {
                instance = getOrCreateSpringBean(className);
                if (Objects.nonNull(instance)) {
                    results.add(buildResult(instance));
                    logger.info("The class successfully loaded into a plugin {} is registered as a spring bean", className);
                }
            } catch (Exception e) {
                logger.error("Registering plugins to spring bean fails: {}", className, e);
            }
        });
        return results;
    }

    private <T> T getOrCreateSpringBean(final String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (SpringContextUtil.getInstance().existBeanByClassName(className)) {
            return SpringContextUtil.getInstance().getBeanByClassName(className);
        }
        lock.lock();
        try {
            T inst = SpringContextUtil.getInstance().getBeanByClassName(className);
            if (Objects.isNull(inst)) {
                Class<?> clazz = Class.forName(className, false, this);
                // Exclude IPlugin subclass || without adding @Component and @Service annotation
                boolean next = IPlugin.class.isAssignableFrom(clazz);
                if (!next) {
                    Annotation[] annotations = clazz.getAnnotations();
                    next = Arrays.stream(annotations).anyMatch(e ->
                            e.annotationType().equals(Component.class) || e.annotationType().equals(Service.class));
                }
                if (next) {
                    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                    beanDefinition.setBeanClassName(className);
                    beanDefinition.setAutowireCandidate(true);
                    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
                    String beanName = SpringContextUtil.getInstance().registerBean(beanDefinition, this);
                    inst = SpringContextUtil.getInstance().getBeanByClassName(beanName);
                }
            }
            return inst;
        } finally {
            lock.unlock();
        }
    }

    private PluginLoaderResult buildResult(final Object instance) {
        PluginLoaderResult result = new PluginLoaderResult();
        if (instance instanceof IPlugin) {
            result.setPlugin((IPlugin) instance);
        }
        return result;
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        if (ability(name)) {
            return this.getParent().loadClass(name);
        }
        Class<?> clazz = classCache.get(name);
        if (clazz != null) {
            return clazz;
        }
        synchronized (this) {
            clazz = classCache.get(name);
            if (clazz == null) {
                String path = classNameToPath(name);
                for (PluginJar each : jars) {
                    ZipEntry entry = each.jarFile.getEntry(path);
                    if (Objects.nonNull(entry)) {
                        try {
                            int index = name.lastIndexOf('.');
                            if (index != -1) {
                                String packageName = name.substring(0, index);
                                definePackageInternal(packageName, each.jarFile.getManifest());
                            }
                            byte[] data = FileCopyUtils.copyToByteArray(each.jarFile.getInputStream(entry));
                            clazz = defineClass(name, data, 0, data.length);
                            classCache.put(name, clazz);
                            return clazz;
                        } catch (final IOException ex) {
                            logger.error("Failed to load class {}.", name, ex);
                        }
                    }
                }
            }
        }
        throw new ClassNotFoundException(String.format("Class name is %s not found.", name));
    }

    @Override
    protected Enumeration<URL> findResources(final String name) throws IOException {
        if (ability(name)) {
            return this.getParent().getResources(name);
        }
        List<URL> resources = new ArrayList<>();
        for (PluginJar each : jars) {
            JarEntry entry = each.jarFile.getJarEntry(name);
            if (Objects.nonNull(entry)) {
                try {
                    resources.add(new URL(String.format("jar:file:%s!/%s", each.sourcePath.getAbsolutePath(), name)));
                } catch (final MalformedURLException ignored) {
                }
            }
        }
        return Collections.enumeration(resources);
    }

    @Override
    protected URL findResource(final String name) {
        if (ability(name)) {
            return this.getParent().getResource(name);
        }
        for (PluginJar each : jars) {
            JarEntry entry = each.jarFile.getJarEntry(name);
            if (Objects.nonNull(entry)) {
                try {
                    return new URL(String.format("jar:file:%s!/%s", each.sourcePath.getAbsolutePath(), name));
                } catch (final MalformedURLException ignored) {
                }
            }
        }
        return null;
    }

    @Override
    public void close() {
        for (PluginJar each : jars) {
            try {
                each.jarFile.close();
            } catch (final IOException ex) {
                logger.error("close error extend plugin jar is ", ex);
            }
        }
    }

    private String classNameToPath(final String className) {
        return String.join("", className.replace(".", "/"), ".class");
    }

    private void definePackageInternal(final String packageName, final Manifest manifest) {
        if (null != getPackage(packageName)) {
            return;
        }
        Attributes attributes = manifest.getMainAttributes();
        String specTitle = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
        String specVersion = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
        String specVendor = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
        String implTitle = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
        String implVersion = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
        String implVendor = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
        definePackage(packageName, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, null);
    }

    private boolean ability(final String name) {
        return !names.contains(name);
    }

    @RequiredArgsConstructor
    private static class PluginJar {
        /**
         * the jar file
         */
        private final JarFile jarFile;
        /**
         * the source path
         */
        private final File sourcePath;
    }
}
