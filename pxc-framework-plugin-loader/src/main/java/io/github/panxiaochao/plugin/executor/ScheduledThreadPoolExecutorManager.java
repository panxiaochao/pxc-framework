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
