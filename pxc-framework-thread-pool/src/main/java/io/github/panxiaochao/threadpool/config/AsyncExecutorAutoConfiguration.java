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
import io.github.panxiaochao.threadpool.properties.ThreadPoolProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * {@code AsyncTaskExecutorConfig}
 * <p> 异步配置类
 *
 * @author Lypxc
 * @since 2022/11/7
 */
@RequiredArgsConstructor
@AutoConfiguration
@EnableAsync(proxyTargetClass = true)
@EnableConfigurationProperties(ThreadPoolProperties.class)
@ConditionalOnProperty(name = "spring.thread.async", havingValue = "true")
public class AsyncExecutorAutoConfiguration implements AsyncConfigurer {

    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncExecutorAutoConfiguration.class);

    /**
     * 属性文件
     */
    private final ThreadPoolProperties threadPoolProperties;

    /**
     * The {@link Executor} instance to be used when processing async
     * method invocations.
     */
    @Nullable
    @Override
    public Executor getAsyncExecutor() {
        LOGGER.info(">>> AsyncExecutor is init success");
        String threadNamePrefix = threadPoolProperties.getThreadpool().getThreadNamePrefix();
        String threadNameGroupName = threadPoolProperties.getThreadpool().getThreadGroupName();
        threadPoolProperties.getThreadpool().setThreadNamePrefix("Async-" + threadNamePrefix);
        threadPoolProperties.getThreadpool().setThreadGroupName("Async-" + threadNameGroupName);
        return new ThreadPoolTaskManager(threadPoolProperties);
    }

    /**
     * The {@link AsyncUncaughtExceptionHandler} instance to be used
     * when an exception is thrown during an asynchronous method execution
     * with {@code void} return type.
     */
    @Nullable
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message - ").append(throwable.getMessage())
                    .append(", Method name - ").append(method.getName());
            if (objects.getClass().isArray() && Objects.nonNull(objects)) {
                sb.append(", Parameter value - ").append(Arrays.toString(objects));
            }
            LOGGER.error(sb.toString());
        };
    }
}
