/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
