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
package io.github.panxiaochao.log.interceptor.httpclient;

import io.github.panxiaochao.common.utils.LocalhostUtil;
import io.github.panxiaochao.common.utils.SpringContextUtil;
import io.github.panxiaochao.log.constant.LogConstants;
import io.github.panxiaochao.log.context.TraceContext;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * {@code TraceHttpClientInterceptor}
 * <p> description: HttpClient Interceptor
 *
 * @author Lypxc
 * @since 2023-01-11
 */
public class TraceHttpClientInterceptor implements HttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceHttpClientInterceptor.class);

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        String traceId = TraceContext.getTraceId();
        if (StringUtils.hasText(traceId)) {
            httpRequest.addHeader(LogConstants.TRACE_ID, traceId);
            httpRequest.addHeader(LogConstants.SPAN_ID, TraceContext.generateNextSpanId());
            httpRequest.addHeader(LogConstants.APPLICATION_NAME, SpringContextUtil.getInstance().getApplicationName());
            httpRequest.addHeader(LogConstants.HOST_NAME, LocalhostUtil.getHostName());
            httpRequest.addHeader(LogConstants.HOST_IP, LocalhostUtil.getHostIp());
        } else {
            LOGGER.debug("[PXC-LOG] threadLocal has no traceId");
        }
    }
}
