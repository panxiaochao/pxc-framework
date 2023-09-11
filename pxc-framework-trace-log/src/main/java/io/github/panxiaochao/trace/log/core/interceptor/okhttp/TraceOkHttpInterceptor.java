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
package io.github.panxiaochao.trace.log.core.interceptor.okhttp;

import io.github.panxiaochao.core.utils.IpUtil;
import io.github.panxiaochao.core.utils.SpringContextUtil;
import io.github.panxiaochao.trace.log.constants.TraceLogConstant;
import io.github.panxiaochao.trace.log.core.context.TraceLogContext;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * <p>
 * OkHttp3 拦截器.
 * </p>
 * <p>
 * 可以通过代码注入：
 * </p>
 * <pre>new OkHttpClient.Builder().addInterceptor(new TraceOkHttpInterceptor());</pre>
 *
 * @author Lypxc
 * @since 2023-09-11
 */
public class TraceOkHttpInterceptor implements Interceptor {

	@NotNull
	@Override
	public Response intercept(@NotNull Chain chain) throws IOException {
		Request.Builder builder = chain.request().newBuilder();
		String traceId = TraceLogContext.getTraceId();
		if (StringUtils.hasText(traceId)) {
			builder.header(TraceLogConstant.TRACE_ID, traceId);
			builder.header(TraceLogConstant.SPAN_ID, TraceLogContext.generateNextSpanId());
			builder.header(TraceLogConstant.PRE_APP, SpringContextUtil.getApplicationName());
			builder.header(TraceLogConstant.PRE_HOST_IP, IpUtil.getHostIp());
			builder.header(TraceLogConstant.PRE_HOST_NAME, IpUtil.getHostName());
		}
		return chain.proceed(builder.build());
	}

}