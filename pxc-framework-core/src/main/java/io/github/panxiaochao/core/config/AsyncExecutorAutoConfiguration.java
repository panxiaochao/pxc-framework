/*
 * Copyright © 2025-2026 Lypxc (545685602@qq.com)
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
package io.github.panxiaochao.core.config;

import io.github.panxiaochao.core.config.properties.PxcFrameWorkProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 异步线程池 自动配置
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-06
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableAsync(proxyTargetClass = true)
@ConditionalOnProperty(name = "spring.pxc-framework.async", havingValue = "true")
public class AsyncExecutorAutoConfiguration implements AsyncConfigurer {

	private final static Logger LOGGER = LoggerFactory.getLogger(AsyncExecutorAutoConfiguration.class);

	/**
	 * 核心线程数, 根据规则生成
	 */
	private final int core = Runtime.getRuntime().availableProcessors() + 1;

	private final PxcFrameWorkProperties pxcFrameWorkProperties;

	@Nullable
	@Override
	public Executor getAsyncExecutor() {
		PxcFrameWorkProperties.ThreadPoolConfig threadPoolConfig = pxcFrameWorkProperties.getThreadPool();
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 核心线程数
		executor.setCorePoolSize(core);
		// 最大线程数
		executor.setMaxPoolSize(core * 2);
		// 队列大小
		executor.setQueueCapacity(threadPoolConfig.getQueueCapacity());
		// 线程活跃时间(秒)
		executor.setKeepAliveSeconds(threadPoolConfig.getKeepAliveSeconds());
		// 线程前缀
		executor.setThreadNamePrefix("async-" + threadPoolConfig.getThreadNamePrefix());
		// 线程分组名称
		executor.setThreadGroupName("async-" + threadPoolConfig.getThreadGroupName());
		// 所有任务结束后关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(threadPoolConfig.isWaitForJobsToCompleteOnShutdown());
		// 拒绝策略 CallerRunsPolicy
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 添加装饰器，上文传递
		executor.setTaskDecorator(new TraceLogCopyContextTaskDecorator());
		// 初始化
		executor.initialize();
		LOGGER.info("配置[AsyncExecutor]成功！");
		return executor;
	}

	/**
	 * 多线程自定义装饰器
	 */
	static class TraceLogCopyContextTaskDecorator implements TaskDecorator {

		/**
		 * Decorate the given {@code Runnable}, returning a potentially wrapped
		 * {@code Runnable} for actual execution, internally delegating to the original
		 * {@link Runnable#run()} implementation.
		 * @param runnable the original {@code Runnable}
		 * @return the decorated {@code Runnable}
		 */
		@Override
		@NonNull
		public Runnable decorate(@NonNull Runnable runnable) {
			// RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			Map<String, String> map = MDC.getCopyOfContextMap();
			return () -> {
				try {
					// if (Objects.nonNull(requestAttributes)) {
					// 	RequestContextHolder.setRequestAttributes(requestAttributes);
					// }
					if (Objects.nonNull(map)) {
						MDC.setContextMap(map);
					}
					runnable.run();
				}
				finally {
					RequestContextHolder.resetRequestAttributes();
					MDC.clear();
				}
			};
		}

	}

	@Nullable
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (throwable, method, objects) -> {
			throwable.printStackTrace();
			StringBuilder sb = new StringBuilder();
			sb.append("Exception message - ")
				.append(throwable.getMessage())
				.append(", Method name - ")
				.append(method.getName());
			if (objects.getClass().isArray() && Objects.nonNull(objects)) {
				sb.append(", Parameter value - ").append(Arrays.toString(objects));
			}
			LOGGER.error(sb.toString());
		};
	}

}
