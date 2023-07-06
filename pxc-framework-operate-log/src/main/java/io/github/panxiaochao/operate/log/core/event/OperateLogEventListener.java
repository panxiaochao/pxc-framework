package io.github.panxiaochao.operate.log.core.event;

import io.github.panxiaochao.operate.log.core.OperateLogDao;
import io.github.panxiaochao.operate.log.core.domain.OperateLogDomain;
import io.github.panxiaochao.operate.log.core.enums.OperateLogType;
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

    private final OperateLogDao operateLogDao;

    /**
     * <pre>
     * 1、可以支持使用异步存储操作
     * 2、自定义存储(数据库、大数据等都可以)或者打印日志
     * </pre
     * @param operateLogDomain 操作日志领域
     */
    @Async
    @EventListener
    public void operateLog(OperateLogDomain operateLogDomain) {
        LOGGER.info("[ip]: {}, [classMethod]: {}, [requestDateTime]: {}, [costTime]: {}ms",
                operateLogDomain.getIp(),
                operateLogDomain.getClassMethod(),
                operateLogDomain.getRequestDateTime(),
                operateLogDomain.getCostTime());
        // 如果是数据库操作
        if (operateLogProperties.logType.equals(OperateLogType.OTHER)) {
            operateLogDao.handle(operateLogDomain);
        }
    }
}