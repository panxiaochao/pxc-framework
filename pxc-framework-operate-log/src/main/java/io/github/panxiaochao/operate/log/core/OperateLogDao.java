package io.github.panxiaochao.operate.log.core;

import io.github.panxiaochao.operate.log.core.domain.OperateLogDomain;
import io.github.panxiaochao.operate.log.core.handler.AbstractOperateLogHandler;
import lombok.RequiredArgsConstructor;

/**
 * {@code OperateLogDao}
 * <p>
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@RequiredArgsConstructor
public class OperateLogDao {

    private final AbstractOperateLogHandler handler;

    /**
     * 处理操作日志
     *
     * @param operateLogDomain operateLogDomain
     */
    public void handle(OperateLogDomain operateLogDomain) {
        handler.saveOperateLog(operateLogDomain);
    }
}
