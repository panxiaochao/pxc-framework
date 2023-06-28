package io.github.panxiaochao.redis.mapper;

import io.github.panxiaochao.core.utils.StringPoolUtil;
import org.redisson.api.NameMapper;
import org.springframework.util.StringUtils;

/**
 * {@code KeyPrefixNameMapper}
 * <p> Redis 缓存 key 自定义前缀
 *
 * @author Lypxc
 * @since 2023-06-27
 */
public class KeyPrefixNameMapper implements NameMapper {

    private final String keyPrefix;

    /**
     * 前缀 默认为空字符串
     */
    public KeyPrefixNameMapper() {
        this.keyPrefix = StringPoolUtil.EMPTY;
    }

    /**
     * 前缀 判断
     *
     * @param keyPrefix 前缀字符串
     */
    public KeyPrefixNameMapper(String keyPrefix) {
        this.keyPrefix = StringUtils.hasText(keyPrefix) ? keyPrefix + StringPoolUtil.COLON : StringPoolUtil.EMPTY;
    }

    /**
     * Applies map function to input <code>name</code>
     *
     * @param name - original Redisson object name
     * @return mapped name
     */
    @Override
    public String map(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        if (StringUtils.hasText(keyPrefix) && !name.startsWith(keyPrefix)) {
            return keyPrefix + name;
        }
        return name;
    }

    /**
     * Applies unmap function to input mapped <code>name</code> to get original name.
     *
     * @param name - mapped name
     * @return original Redisson object name
     */
    @Override
    public String unmap(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        if (StringUtils.hasText(keyPrefix) && name.startsWith(keyPrefix)) {
            return name.substring(keyPrefix.length());
        }
        return name;
    }
}
