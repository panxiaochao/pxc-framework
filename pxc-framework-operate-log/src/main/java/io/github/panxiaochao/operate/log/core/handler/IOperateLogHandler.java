package io.github.panxiaochao.operate.log.core.handler;

import io.github.panxiaochao.operate.log.core.domain.OperateLogDomain;

/**
 * {@code IOperateLogHandler}
 * <p>
 *
 * @author Lypxc
 * @since 2023-07-03
 */
public interface IOperateLogHandler {

    /**
     * 日志存储数据库
     *
     * @param operateLogDomain 存储对象
     */
    void saveOperateLog(OperateLogDomain operateLogDomain);
}
