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
package io.github.panxiaochao.trace.log.core.domain;

import io.github.panxiaochao.core.utils.IpUtil;
import io.github.panxiaochao.core.utils.StringPools;
import io.github.panxiaochao.core.utils.UuidUtil;
import io.github.panxiaochao.trace.log.constants.TraceLogConstant;
import io.github.panxiaochao.trace.log.core.context.TraceLogContext;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * <p>
 * 日志追踪Builder类
 * </p>
 *
 * @author Lypxc
 * @since 2023-08-15
 */
@Getter
public class TraceLogDomain implements Serializable {

	private static final long serialVersionUID = 5728436343648917967L;

	private Map<String, String> attributes;

	private TraceLogDomain() {
	}

	public TraceLogDomain(TraceLogDomainBuilder builder) {
		// traceId 如果没有取到TraceId，就重新生成一个
		if (StringUtils.isBlank(builder.getTraceId())) {
			builder.setTraceId(UuidUtil.getSimpleUUID());
		}
		TraceLogContext.setTraceId(builder.getTraceId());
		// spanId 如果为空，会放入初始值
		TraceLogContext.setSpanId(builder.getSpanId());
		// 本机IP
		TraceLogContext.setHostIp(builder.getHostIp());
		// 本机名
		TraceLogContext.setHostName(builder.getHostName());
	}

	/**
	 * 格式化生成打印日志标签
	 * @return 日志标签
	 */
	public String formatTraceLogLabel() {
		StringJoiner traceLogLabel = new StringJoiner(StringPools.COMMA, "[", "]");
		// 额外属性添加
		// this.attributes.forEach((key, value) -> {
		// if (StringUtils.isBlank(value)) {
		// traceLogLabel.add(TraceLogConstant.UNKNOWN);
		// }
		// else {
		// traceLogLabel.add(value);
		// }
		// });
		// traceLogLabel.add(SpringContextUtil.getApplicationName());
		traceLogLabel.add(TraceLogContext.getSpanId());
		traceLogLabel.add(TraceLogContext.getTraceId());
		traceLogLabel.add(TraceLogContext.getHostIp());
		traceLogLabel.add(TraceLogContext.getHostName());
		return traceLogLabel.toString();
	}

	/**
	 * Builder构造
	 * @param request HttpServletRequest
	 * @return TraceLogDomainBuilder
	 */
	public static TraceLogDomainBuilder withServletRequest(HttpServletRequest request) {
		Assert.notNull(request, "request cannot be null");
		return new TraceLogDomainBuilder(request);
	}

	@Getter
	@Setter
	public static final class TraceLogDomainBuilder implements Serializable {

		private static final long serialVersionUID = 5728436343648917967L;

		/**
		 * 链路唯一ID
		 */
		private String traceId;

		/**
		 * 链路节点
		 */
		private String spanId;

		/**
		 * 当前ip
		 */
		private String hostIp;

		/**
		 * 当前HostName
		 */
		private String hostName;

		private final Map<String, String> attributes = new HashMap<>();

		private TraceLogDomainBuilder(HttpServletRequest request) {
			// 获取 RequestHead 信息
			this.traceId = request.getHeader(TraceLogConstant.TRACE_ID);
			this.spanId = request.getHeader(TraceLogConstant.SPAN_ID);
			this.hostIp = IpUtil.getHostIp();
			this.hostName = IpUtil.getHostName();
			// 额外属性
			attributes.put(TraceLogConstant.PRE_APP, request.getHeader(TraceLogConstant.PRE_APP));
			attributes.put(TraceLogConstant.PRE_HOST_IP, request.getHeader(TraceLogConstant.PRE_HOST_IP));
			attributes.put(TraceLogConstant.PRE_HOST_NAME, request.getHeader(TraceLogConstant.PRE_HOST_NAME));
		}

		public TraceLogDomain build() {
			TraceLogDomain traceLogDomain = new TraceLogDomain(this);
			traceLogDomain.attributes = Collections.unmodifiableMap(this.attributes);
			return traceLogDomain;
		}

	}

}
