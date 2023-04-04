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
package io.github.panxiaochao.log.interceptor.resttemplate;

import io.github.panxiaochao.common.utils.LocalhostUtil;
import io.github.panxiaochao.common.utils.SpringContextUtil;
import io.github.panxiaochao.log.constant.LogConstants;
import io.github.panxiaochao.log.context.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * {@code TraceRestTemplateInterceptor}
 * <p> description: RestTemplate Interceptor
 *
 * @author Lypxc
 * @since 2023-01-13
 */
public class TraceRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceRestTemplateInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String traceId = TraceContext.getTraceId();
        if (StringUtils.hasText(traceId)) {
            request.getHeaders().add(LogConstants.TRACE_ID, traceId);
            request.getHeaders().add(LogConstants.SPAN_ID, TraceContext.generateNextSpanId());
            request.getHeaders().add(LogConstants.APPLICATION_NAME, SpringContextUtil.getApplicationName());
            request.getHeaders().add(LogConstants.HOST_NAME, LocalhostUtil.getHostName());
            request.getHeaders().add(LogConstants.HOST_IP, LocalhostUtil.getHostIp());
        } else {
            LOGGER.debug("[PXC-LOG] threadLocal has no traceId");
        }
        return execution.execute(request, body);
    }
}
