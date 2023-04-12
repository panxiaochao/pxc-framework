package io.github.panxiaochao.plugin.config;

import io.github.panxiaochao.plugin.executor.ScheduledThreadPoolExecutorManager;
import io.github.panxiaochao.plugin.properties.PluginLoaderProperties;
import io.github.panxiaochao.plugin.service.PluginLoaderService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * {@code PluginLoaderAutoConfiguration}
 * <p> description: Plugin Loader AutoConfiguration
 *
 * @author Lypxc
 * @since 2023-04-11
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties(PluginLoaderProperties.class)
@ConditionalOnProperty(name = "plugin-ext.enabled", havingValue = "true")
public class PluginLoaderAutoConfiguration {

    /**
     * ScheduledThreadPoolExecutor Manager
     *
     * @param pluginLoaderProperties the plugin loader properties
     * @return the ScheduledThreadPoolExecutor Manager
     */
    @Bean
    public ScheduledThreadPoolExecutorManager scheduledThreadPoolExecutor(final PluginLoaderProperties pluginLoaderProperties) {
        return new ScheduledThreadPoolExecutorManager(pluginLoaderProperties);
    }

    /**
     * the bean plugin loader service
     *
     * @param pluginLoaderProperties the plugin loader properties
     * @return the PluginLoaderService
     */
    @Bean
    public PluginLoaderService pluginLoaderService(final PluginLoaderProperties pluginLoaderProperties, final ScheduledThreadPoolExecutorManager scheduledThreadPoolExecutorManager) {
        return new PluginLoaderService(pluginLoaderProperties, scheduledThreadPoolExecutorManager);
    }
}
