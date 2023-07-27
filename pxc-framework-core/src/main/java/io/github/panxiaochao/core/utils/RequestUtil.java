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
package io.github.panxiaochao.core.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Request相关请求工具类
 * </p>
 *
 * @author Lypxc
 * @since 2023-05-06
 */
public class RequestUtil {

	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	/**
	 * 未知
	 */
	public static final String UNKNOWN = "unknown";

	/**
	 * Ipv4 和 Ipv6 本地地址
	 */
	private static final List<String> LOCAL_HOST_ARR = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");

	/**
	 *
	 * <p>
	 * 获取客户端IP
	 * </p>
	 * <pre>
	 *     1、x-Original-Forwarded-For（k8s）
	 *     2、X-Forwarded-For
	 *     3、X-Real-IP
	 *     4、Proxy-Client-IP
	 *     5、WL-Proxy-Client-IP
	 *     6、HTTP_CLIENT_IP
	 *     7、HTTP_X_FORWARDED_FOR
	 * </pre>
	 * @return IP
	 */
	public static String ofRequestIp() {
		return null == getRequest() ? "" : ofRequestIp(getRequest(), ArrayUtil.EMPTY_STRING_ARRAY);
	}

	/**
	 *
	 * <p>
	 * 获取客户端IP
	 * </p>
	 * <pre>
	 *     1、x-Original-Forwarded-For（k8s）
	 *     2、X-Forwarded-For
	 *     3、X-Real-IP
	 *     4、Proxy-Client-IP
	 *     5、WL-Proxy-Client-IP
	 *     6、HTTP_CLIENT_IP
	 *     7、HTTP_X_FORWARDED_FOR
	 * </pre>
	 * @param request 请求对象
	 * @return IP
	 */
	public static String ofRequestIp(HttpServletRequest request) {
		return ofRequestIp(request, ArrayUtil.EMPTY_STRING_ARRAY);
	}

	/**
	 * <p>
	 * 获取客户端IP
	 * </p>
	 * @param request 请求对象
	 * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
	 * @return IP
	 */
	public static String ofRequestIp(HttpServletRequest request, String... headerNames) {
		String[] headers = { "x-Original-Forwarded-For", "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP",
				"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
		if (ArrayUtil.isNotEmpty(headerNames)) {
			headers = ArrayUtil.addAll(headers, headerNames);
		}
		String ip;
		for (String header : headers) {
			ip = request.getHeader(header);
			if (Boolean.FALSE.equals(isUnknown(ip))) {
				return checkLocalHost(getMultistageReverseProxyIp(ip));
			}
		}
		ip = request.getRemoteAddr();
		return checkLocalHost(getMultistageReverseProxyIp(ip));
	}

	/**
	 * 检查是否是本地地址
	 * @param ip ip
	 * @return ip
	 */
	private static String checkLocalHost(String ip) {
		if (LOCAL_HOST_ARR.contains(ip)) {
			// 根据网卡取本机配置的IP
			InetAddress iNet;
			try {
				iNet = InetAddress.getLocalHost();
				ip = iNet.getHostAddress();
			}
			catch (Exception e) {
				logger.error("checkLocalHost error", e);
			}
		}
		return ip;
	}

	/**
	 * 从多级反向代理中获得第一个非unknown IP地址
	 * @param ip 获得的IP地址
	 * @return 第一个非unknown IP地址
	 */
	private static String getMultistageReverseProxyIp(String ip) {
		// 多级反向代理检测
		if (ip != null && StringUtils.indexOf(ip, ',') > 0) {
			final String[] ips = StringUtils.split(ip, ',');
			for (final String subIp : ips) {
				if (Boolean.FALSE.equals(isUnknown(subIp))) {
					ip = subIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * 检测给定字符串是否为 unknown，多用于检测HTTP请求相关
	 * @param checkString 被检测的字符串
	 * @return 是否未知
	 */
	private static Boolean isUnknown(String checkString) {
		return StrUtil.isBlank(checkString) || UNKNOWN.equalsIgnoreCase(checkString);
	}

	/**
	 * obtain HttpServletRequest
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		return null == getRequestAttributes() ? null : getRequestAttributes().getRequest();
	}

	/**
	 * obtain HttpServletResponse
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		return null == getRequestAttributes() ? null : getRequestAttributes().getResponse();
	}

	/**
	 * obtain ServerRequestAttributes
	 * @return ServletRequestAttributes
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return null == attributes ? null : (ServletRequestAttributes) attributes;
	}

	/**
	 * 获得所有请求参数
	 * @return Map
	 */
	public static Map<String, String[]> getParams() {
		if (null == getRequest()) {
			return MapUtil.newHashMap();
		}
		final Map<String, String[]> map = getRequest().getParameterMap();
		return Collections.unmodifiableMap(map);

	}

	/**
	 * 获得所有请求参数
	 * @param request 请求对象{@link HttpServletRequest}
	 * @return Map
	 */
	public static Map<String, String[]> getParams(HttpServletRequest request) {
		final Map<String, String[]> map = request.getParameterMap();
		return Collections.unmodifiableMap(map);
	}

	/**
	 * 获得所有请求参数
	 * @return Map
	 */
	public static Map<String, String> getParamMap() {
		Map<String, String> params = MapUtil.newHashMap();
		for (Map.Entry<String, String[]> entry : getParams().entrySet()) {
			params.put(entry.getKey(), String.join(StringPoolUtil.COMMA, entry.getValue()));
		}
		return params;
	}

	/**
	 * 获得所有请求参数
	 * @param request 请求对象{@link HttpServletRequest}
	 * @return Map
	 */
	public static Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String> params = MapUtil.newHashMap();
		for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
			params.put(entry.getKey(), String.join(StringPoolUtil.COMMA, entry.getValue()));
		}
		return params;
	}

	/**
	 * 一次性获取请求体String - 注意：调用该方法后，getParam方法将失效
	 * @param request request
	 * @return byte[]
	 */
	public static String getBodyString(ServletRequest request) {
		try {
			return IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 一次性获取请求体byte[] - 注意：调用该方法后，getParam方法将失效
	 * @param request request
	 * @return byte[]
	 */
	public static byte[] getBodyBytes(ServletRequest request) {
		try {
			return IOUtils.toByteArray(request.getInputStream());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
