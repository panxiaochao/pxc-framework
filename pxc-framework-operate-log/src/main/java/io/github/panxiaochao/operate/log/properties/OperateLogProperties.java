package io.github.panxiaochao.operate.log.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code OperateLogProperties}
 * <p> description: OperateLogProperties
 *
 * @author Lypxc
 * @since 2023-06-20
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "spring.operatelog", ignoreInvalidFields = true)
public class OperateLogProperties {

    /**
     * 是否开启
     */
    private boolean enabled;
}
