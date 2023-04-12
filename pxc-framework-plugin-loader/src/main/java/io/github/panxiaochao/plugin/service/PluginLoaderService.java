package io.github.panxiaochao.plugin.service;

import io.github.panxiaochao.plugin.executor.ScheduledThreadPoolExecutorManager;
import io.github.panxiaochao.plugin.loader.PluginLoader;
import io.github.panxiaochao.plugin.loader.PluginLoaderResult;
import io.github.panxiaochao.plugin.properties.PluginLoaderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * {@code PluginLoaderService}
 * <p> description:  Plugin Loader Service
 *
 * @author Lypxc
 * @since 2023-04-11
 */
public class PluginLoaderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PluginLoaderProperties pluginLoaderProperties;

    public PluginLoaderService(PluginLoaderProperties pluginLoaderProperties, ScheduledThreadPoolExecutorManager scheduledThreadPoolExecutorManager) {
        this.pluginLoaderProperties = pluginLoaderProperties;
        if (pluginLoaderProperties.isEnabled()) {
            scheduledThreadPoolExecutorManager.scheduleAtFixedRate(
                    this::loadExtendPlugins,
                    pluginLoaderProperties.getScheduleDelay(),
                    pluginLoaderProperties.getScheduleTime(),
                    TimeUnit.SECONDS
            );
        }
    }

    public List<PluginLoaderResult> loadExtendPlugins() {
        List<PluginLoaderResult> results = null;
        try {
            results = PluginLoader.getInstance().loadExtendPlugins(pluginLoaderProperties.getPath());
        } catch (Exception e) {
            logger.error("loader ext plugins has error ", e);
        }
        return results;
    }
}
