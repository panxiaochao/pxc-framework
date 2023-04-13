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
package io.github.panxiaochao.log.core.webflux;

import io.github.panxiaochao.common.utils.LocalhostUtil;
import io.github.panxiaochao.common.utils.SpringContextUtil;
import io.github.panxiaochao.log.constant.LogConstants;
import io.github.panxiaochao.log.context.TraceContext;
import io.github.panxiaochao.log.core.mdc.MDCHelper;
import io.github.panxiaochao.log.core.web.AbstractProcessTraceHandler;
import io.github.panxiaochao.log.utils.TraceIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * {@code TraceWebMvcSupport}
 * <p> description: Mvc 支持生成TraceId方法类
 *
 * @author Lypxc
 * @since 2023-01-16
 */
public class ProcessTraceWebFluxHandler extends AbstractProcessTraceHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessTraceWebFluxHandler.class);

    /**
     * volatile是为了保证内存可见性，防止编译器过度优化（指令重排序）
     */
    private static volatile ProcessTraceWebFluxHandler processTraceWebMvcHandler = null;

    /**
     * 饿汉模式，多线程安全
     *
     * @return 初始化实例
     */
    public static ProcessTraceWebFluxHandler INSTANCE() {
        if (processTraceWebMvcHandler == null) {
            synchronized (ProcessTraceWebFluxHandler.class) {
                if (processTraceWebMvcHandler == null) {
                    processTraceWebMvcHandler = new ProcessTraceWebFluxHandler();
                }
            }
        }
        return processTraceWebMvcHandler;
    }

    /**
     * 处理前置追踪日志
     *
     * @param exchange exchange
     */
    @Override
    public ServerWebExchange processBeforeTrace(ServerWebExchange exchange) {
        Map<String, String> traceContext = new HashMap<>(10);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // 获取Head信息
        List<String> headerList = headers.get(LogConstants.TRACE_ID);
        String traceId = CollectionUtils.isEmpty(headerList) ? null : headerList.get(0);
        headerList = headers.get(LogConstants.SPAN_ID);
        String spanId = CollectionUtils.isEmpty(headerList) ? null : headerList.get(0);
        headerList = headers.get(LogConstants.HOST_IP);
        String hostIp = CollectionUtils.isEmpty(headerList) ? null : headerList.get(0);
        headerList = headers.get(LogConstants.HOST_NAME);
        String hostName = CollectionUtils.isEmpty(headerList) ? null : headerList.get(0);

        if (StringUtils.hasText(hostIp)) {
            TraceContext.setHostIp(hostIp);
        } else {
            TraceContext.setHostIp(LocalhostUtil.getHostIp());
        }
        if (StringUtils.hasText(hostName)) {
            TraceContext.setHostName(hostName);
        } else {
            TraceContext.setHostName(LocalhostUtil.getHostName());
        }
        // 如果没有取到TraceId，就重新生成一个
        if (!StringUtils.hasText(traceId)) {
            traceId = TraceIdUtils.uuid();
        }
        TraceContext.setTraceId(traceId);
        // 如果spanId为空，会放入初始值
        TraceContext.setSpanId(spanId);
        // label log
        String labelLog = LogConstants.LOGGER_PATTERN
                .replace(LogConstants.APPLICATION_NAME, SpringContextUtil.getInstance().getApplicationName())
                .replace(LogConstants.TRACE_ID, TraceContext.getTraceId())
                .replace(LogConstants.SPAN_ID, TraceContext.getSpanId())
                .replace(LogConstants.HOST_IP, TraceContext.getHostIp())
                .replace(LogConstants.HOST_NAME, TraceContext.getHostName());

        // 组装traceContext
        traceContext.put(LogConstants.MDC_KEY, labelLog);
        traceContext.put(LogConstants.TRACE_ID, TraceContext.getTraceId());
        traceContext.put(LogConstants.SPAN_ID, TraceContext.getSpanId());
        traceContext.put(LogConstants.HOST_IP, TraceContext.getHostIp());
        traceContext.put(LogConstants.HOST_NAME, TraceContext.getHostName());
        // 置入MDC
        MDCHelper.putContextMap(traceContext);
        // Header传递日志链路, 去除MDC_KEY, 因为里面有制表符，httpHeader.set不支持
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set(LogConstants.TRACE_ID, TraceContext.getTraceId());
            httpHeader.set(LogConstants.SPAN_ID, TraceContext.getSpanId());
            httpHeader.set(LogConstants.HOST_IP, TraceContext.getHostIp());
            httpHeader.set(LogConstants.HOST_NAME, TraceContext.getHostName());
        };
        return exchange.mutate()
                .request(
                        exchange.getRequest().mutate()
                                .headers(httpHeaders)
                                .build())
                .build();
    }

    /**
     * 清除日志记录
     */
    @Override
    public void cleanTrace() {
        TraceContext.removeAll();
        MDCHelper.removeAll();
    }
}
