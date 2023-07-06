package io.github.panxiaochao.core.config;

import io.github.panxiaochao.core.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * {@code AsyncExecutorAutoConfiguration}
 * <p> 异步线程池 自动配置
 *
 * @author Lypxc
 * @since 2023-07-06
 */
@AutoConfiguration
@EnableAsync(proxyTargetClass = true)
@ConditionalOnProperty(name = "spring.pxc-framework.async", havingValue = "true")
public class AsyncExecutorAutoConfiguration implements AsyncConfigurer {

    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncExecutorAutoConfiguration.class);

    @Nullable
    @Override
    public Executor getAsyncExecutor() {
        LOGGER.info("配置[AsyncExecutor]成功！");
        return SpringContextUtil.getBean("threadPoolTaskExecutor");
    }

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
