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
package io.github.panxiaochao.threadpool.config;

import io.github.panxiaochao.threadpool.executor.ThreadPoolTaskManager;
import io.github.panxiaochao.threadpool.executor.ThreadPoolTaskSchedulerManager;
import io.github.panxiaochao.threadpool.properties.ThreadPoolProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * {@code AsyncTaskExecutorConfig}
 * <p> 异步配置类
 *
 * @author Lypxc
 * @since 2022/11/7
 */
@RequiredArgsConstructor
@Configuration
@EnableScheduling
@EnableConfigurationProperties(ThreadPoolProperties.class)
@ConditionalOnProperty(name = ThreadPoolProperties.THREAD_POOL_ENABLED, havingValue = "true")
@ConditionalOnWebApplication
public class TaskExecutorAutoConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(TaskExecutorAutoConfiguration.class);

    /**
     * 属性文件
     */
    private final ThreadPoolProperties threadPoolProperties;

    /**
     * 构造 多线程任务配置
     *
     * @return ThreadPoolTaskManager
     */
    @Bean(name = "threadPoolTaskManager")
    public ThreadPoolTaskManager threadPoolTaskManager() {
        LOGGER.info(">>> ThreadPoolTaskManager is init success");
        return new ThreadPoolTaskManager(threadPoolProperties);
    }

    /**
     * 构造 定时多线程任务配置
     *
     * @return ThreadPoolTaskSchedulerManager
     */
    @Bean(name = "threadPoolTaskSchedulerManager")
    public ThreadPoolTaskSchedulerManager threadPoolTaskSchedulerManager() {
        LOGGER.info(">>> ThreadPoolTaskSchedulerManager is init success");
        return new ThreadPoolTaskSchedulerManager(threadPoolProperties);
    }
}
