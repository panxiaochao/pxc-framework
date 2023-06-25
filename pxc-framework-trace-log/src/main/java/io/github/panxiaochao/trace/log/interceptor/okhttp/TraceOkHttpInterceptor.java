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
package io.github.panxiaochao.trace.log.interceptor.okhttp;

import io.github.panxiaochao.common.utils.LocalhostUtil;
import io.github.panxiaochao.common.utils.SpringContextUtil;
import io.github.panxiaochao.trace.log.constant.LogConstants;
import io.github.panxiaochao.trace.log.context.TraceContext;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * {@code TraceOkHttpInterceptor}
 * <p> description: OkHttp3 Interceptor
 *
 * @author Lypxc
 * @since 2023-01-13
 */
public class TraceOkHttpInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceOkHttpInterceptor.class);

    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String traceId = TraceContext.getTraceId();
        if (StringUtils.hasText(traceId)) {
            builder.header(LogConstants.TRACE_ID, traceId);
            builder.header(LogConstants.SPAN_ID, TraceContext.generateNextSpanId());
            builder.header(LogConstants.APPLICATION_NAME, SpringContextUtil.getInstance().getApplicationName());
            builder.header(LogConstants.HOST_NAME, LocalhostUtil.getHostName());
            builder.header(LogConstants.HOST_IP, LocalhostUtil.getHostIp());
        } else {
            LOGGER.debug("[PXC-LOG] threadLocal has no traceId");
        }
        return chain.proceed(builder.build());
    }
}
