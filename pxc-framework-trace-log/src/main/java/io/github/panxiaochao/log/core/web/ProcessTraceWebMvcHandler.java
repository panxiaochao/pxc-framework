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

import io.github.panxiaochao.common.utils.LocalhostUtil;
import io.github.panxiaochao.common.utils.SpringContextUtil;
import io.github.panxiaochao.log.constant.LogConstants;
import io.github.panxiaochao.log.context.TraceContext;
import io.github.panxiaochao.log.core.mdc.MDCHelper;
import io.github.panxiaochao.log.utils.TraceIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code TraceWebMvcSupport}
 * <p> description: Mvc 支持生成TraceId方法类
 *
 * @author Lypxc
 * @since 2023-01-16
 */
public class ProcessTraceWebMvcHandler extends AbstractProcessTraceHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessTraceWebMvcHandler.class);

    /**
     * volatile是为了保证内存可见性，防止编译器过度优化（指令重排序）
     */
    private static volatile ProcessTraceWebMvcHandler processTraceWebMvcHandler = null;

    /**
     * 饿汉模式，多线程安全
     *
     * @return 初始化实例
     */
    public static ProcessTraceWebMvcHandler INSTANCE() {
        if (null == processTraceWebMvcHandler) {
            synchronized (ProcessTraceWebMvcHandler.class) {
                if (null == processTraceWebMvcHandler) {
                    processTraceWebMvcHandler = new ProcessTraceWebMvcHandler();
                }
            }
        }
        return processTraceWebMvcHandler;
    }

    /**
     * 处理前置追踪日志
     *
     * @param request request
     */
    @Override
    public void processBeforeTrace(HttpServletRequest request) {
        Map<String, String> traceContext = new HashMap<>(10);
        // 获取Head信息
        String traceId = request.getHeader(LogConstants.TRACE_ID);
        String spanId = request.getHeader(LogConstants.SPAN_ID);
        String hostIp = request.getHeader(LogConstants.HOST_IP);
        String hostName = request.getHeader(LogConstants.HOST_NAME);

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
