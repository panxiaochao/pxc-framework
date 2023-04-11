package io.github.panxiaochao.plugin.config;

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
@ConditionalOnProperty(name = "plugin-ext.enable", havingValue = "true")
public class PluginLoaderAutoConfiguration {

    @Bean
    public PluginLoaderService pluginLoaderService(final PluginLoaderProperties pluginLoaderProperties) {
        return new PluginLoaderService(pluginLoaderProperties);
    }
}
