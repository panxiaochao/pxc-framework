package io.github.panxiaochao.log.config;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * {@code TraceDubboAutoConfiguration}
 * <p> description: Dubbo Log Trace Configuration
 *
 * @author Lypxc
 * @since 2023-04-10
 */
@Activate(group = {PROVIDER, CONSUMER})
public class TraceDubboAutoConfiguration implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraceDubboAutoConfiguration.class);

    @PostConstruct
    public void init() {
        LOGGER.info(">>> TraceDubbo init");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return null;
    }
}
