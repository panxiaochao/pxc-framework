package io.github.panxiaochao.operate.log.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code AbstractOperateLogHandler}
 * <p> 抽象类
 *
 * @author Lypxc
 * @since 2023-07-03
 */
public abstract class AbstractOperateLogHandler implements IOperateLogHandler {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
