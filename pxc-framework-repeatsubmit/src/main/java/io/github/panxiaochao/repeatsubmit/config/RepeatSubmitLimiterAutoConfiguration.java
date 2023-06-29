package io.github.panxiaochao.repeatsubmit.config;

import io.github.panxiaochao.repeatsubmit.aspect.RepeatSubmitLimiterAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConfiguration;

/**
 * {@code RepeatSubmitLimiterAutoConfiguration}
 * <p> RepeatSubmitLimiter 自动配置类
 *
 * @author Lypxc
 * @since 2023-06-28
 */
@AutoConfiguration(after = RedisConfiguration.class)
public class RepeatSubmitLimiterAutoConfiguration {
    @Bean
    public RepeatSubmitLimiterAspect repeatSubmitLimiterAspect() {
        return new RepeatSubmitLimiterAspect();
    }
}
