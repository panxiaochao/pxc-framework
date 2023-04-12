package io.github.panxiaochao.plugin.executor;

import io.github.panxiaochao.plugin.properties.PluginLoaderProperties;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code ScheduledThreadPoolExecutorManager}
 * <p> description: Scheduled ThreadPool Executor Manager
 *
 * @author Lypxc
 * @since 2023-04-12
 */
public class ScheduledThreadPoolExecutorManager extends ScheduledThreadPoolExecutor {

    public ScheduledThreadPoolExecutorManager(final PluginLoaderProperties pluginLoaderProperties) {
        super(pluginLoaderProperties.getCorePoolSize(), new PluginLoaderThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
