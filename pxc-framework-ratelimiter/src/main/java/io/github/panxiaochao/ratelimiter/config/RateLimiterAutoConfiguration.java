package io.github.panxiaochao.ratelimiter.config;

import io.github.panxiaochao.ratelimiter.aspect.RateLimiterAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConfiguration;

/**
 * {@code RateLimiterAutoConfiguration}
 * <p> RateLimiter 自动配置类
 *
 * @author Lypxc
 * @since 2023-06-28
 */
@AutoConfiguration(after = RedisConfiguration.class)
public class RateLimiterAutoConfiguration {
    @Bean
    public RateLimiterAspect rateLimiterAspect() {
        return new RateLimiterAspect();
    }
}
