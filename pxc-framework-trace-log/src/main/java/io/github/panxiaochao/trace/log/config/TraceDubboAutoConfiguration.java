/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.trace.log.config;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

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
@ConditionalOnClass(name = {"org.apache.dubbo.rpc.Filter"})
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
