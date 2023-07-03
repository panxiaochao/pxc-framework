package io.github.panxiaochao.operate.log.event;

import io.github.panxiaochao.operate.log.domain.OperateLogDomain;
import io.github.panxiaochao.operate.log.enums.OperateLogType;
import io.github.panxiaochao.operate.log.properties.OperateLogProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * {@code OperateLogEventListener}
 * <p> 操作日志监听类
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@RequiredArgsConstructor
@AutoConfiguration
@EnableConfigurationProperties(OperateLogProperties.class)
public class OperateLogEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogEventListener.class);

    private final OperateLogProperties operateLogProperties;

    /**
     * 可以支持使用异步存储或者打印日志
     *
     * @param operateLogDomain 操作日志领域
     */
    @Async
    @EventListener
    public void operateLog(OperateLogDomain operateLogDomain) {
        if (operateLogProperties.logType.equals(OperateLogType.LOGGER)) {
            LOGGER.info("[ip]: {}, [classMethod]: {}, [requestDateTime]: {}, [costTime]: {}ms",
                    operateLogDomain.getIp(),
                    operateLogDomain.getClassMethod(),
                    operateLogDomain.getRequestDateTime(),
                    operateLogDomain.getCostTime());
        } else {
            LOGGER.info("OperateLogEventListener DB");
        }
    }
}
