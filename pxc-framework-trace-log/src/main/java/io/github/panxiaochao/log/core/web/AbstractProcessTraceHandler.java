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
package io.github.panxiaochao.log.core.web;

import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code AbstractProcessTraceHandler}
 * <p> description: 抽象处理日志
 *
 * @author Lypxc
 * @since 2023-01-17
 */
public abstract class AbstractProcessTraceHandler {

    /**
     * 处理前置追踪日志
     *
     * @param request request
     */
    public void processBeforeTrace(HttpServletRequest request) {
    }

    /**
     * 处理前置追踪日志
     *
     * @param exchange 上下文
     * @return ServerWebExchange
     */
    public ServerWebExchange processBeforeTrace(ServerWebExchange exchange) {
        return exchange;
    }

    /**
     * 处理后置追踪日志
     */
    public void processAfterTrace() {
    }

    ;

    /**
     * 清除日志记录
     */
    public abstract void cleanTrace();

}
