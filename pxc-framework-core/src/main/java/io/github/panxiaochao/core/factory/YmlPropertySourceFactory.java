package io.github.panxiaochao.core.factory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * {@code YmlPropertySourceFactory}
 * <p> YML property factory
 *
 * @author Lypxc
 * @since 2022/6/25
 */
public class YmlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    @Nullable
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        String sourceName = resource.getResource().getFilename();
        if (StringUtils.hasText(sourceName)
                && (StringUtils.endsWithIgnoreCase(sourceName, ".yml")
                || StringUtils.endsWithIgnoreCase(sourceName, ".yaml"))) {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return new PropertiesPropertySource(sourceName, Objects.requireNonNull(factory.getObject()));
        }
        return super.createPropertySource(name, resource);
    }

}
