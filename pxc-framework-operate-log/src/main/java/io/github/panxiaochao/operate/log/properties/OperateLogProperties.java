package io.github.panxiaochao.operate.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code OperateLogProperties}
 * <p> description: OperateLog properties
 *
 * @author Lypxc
 * @since 2023-06-08
 */
@Getter
@Setter
@ConfigurationProperties(prefix = OperateLogProperties.OPERATE_LOG_PREFIX, ignoreInvalidFields = true)
public class OperateLogProperties {

    /**
     * 属性前缀
     */
    public static final String OPERATE_LOG_PREFIX = "spring.operatelog";

    /**
     * 是否开启
     */
    private boolean enabled;
}
