package io.github.panxiaochao.redis.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code PxcRedisProperties}
 * <p> description: PxcRedisProperties
 *
 * @author Lypxc
 * @since 2023-06-20
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "spring.redis", ignoreInvalidFields = true)
public class PxcRedisProperties {

    /**
     * 是否开启限流限量
     */
    private boolean accessRateLimit;
}
