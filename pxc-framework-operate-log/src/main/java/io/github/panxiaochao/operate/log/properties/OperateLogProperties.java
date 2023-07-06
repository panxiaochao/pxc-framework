package io.github.panxiaochao.operate.log.properties;

import io.github.panxiaochao.operate.log.core.enums.OperateLogType;
import io.github.panxiaochao.operate.log.core.handler.AbstractOperateLogHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code OperateLogProperties}
 * <p> 操作日志属性
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "spring.operatelog", ignoreInvalidFields = true)
public class OperateLogProperties {

    /**
     * 存储日志类型
     */
    public OperateLogType logType = OperateLogType.LOGGER;

    /**
     * 自定义日志处理器,
     */
    private Class<? extends AbstractOperateLogHandler> handler;
}