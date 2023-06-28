package io.github.panxiaochao.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code Redisson3Properties}
 * <p> Redisson 自定义属性
 *
 * @author Lypxc
 * @since 2023-06-27
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.redis", ignoreInvalidFields = true)
public class Redisson3Properties {
    /**
     * redis 缓存 key 前缀
     */
    private String keyPrefix;
}
