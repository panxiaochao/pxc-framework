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
package io.github.panxiaochao.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;

/**
 * {@code RequestUtil}
 * <p> description: 请求工具类
 *
 * @author Lypxc
 * @since 2023-05-06
 */
public class RequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    /**
     * <p>未知
     */
    public static final String UNKNOWN = "unknown";

    private static final String LOCAL_HOST_IP_1 = "0:0:0:0:0:0:0:1";

    private static final String LOCAL_HOST_IP_2 = "127.0.0.1";

    /**
     * 获取请求 IP
     *
     * <p>使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * <p>使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     *
     * @return 返回IP
     */
    public static String ofRequestIp() {
        return ofRequestIp(getRequest());
    }

    /**
     * 获取请求 IP
     *
     * <p>使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * <p>使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     *
     * @param request 请求包装
     * @return 返回IP
     */
    public static String ofRequestIp(HttpServletRequest request) {
        String ip = null;
        try {
            // 以下两个获取在k8s中，将真实的客户端IP，放到了x-Original-Forwarded-For。
            // 而将 WAF 的回源地址放到了x-Forwarded-For 了。
            ip = request.getHeader("X-Original-Forwarded-For");
            if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            // 获取 nginx 等代理的 IP
            if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-forwarded-for");
            }
            if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (StringUtils.hasText(ip) && !isValidAddress(ip)) {
                    return null;
                }
            }
            if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (StringUtils.hasText(ip) && !isValidAddress(ip)) {
                    return null;
                }
            }
            if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (StringUtils.hasText(ip) && !isValidAddress(ip)) {
                    return null;
                }
            }
            if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (StringUtils.hasText(ip) && !isValidAddress(ip)) {
                    return null;
                }
            }
            if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (LOCAL_HOST_IP_1.equalsIgnoreCase(ip) || LOCAL_HOST_IP_2.equalsIgnoreCase(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress iNet = null;
                try {
                    iNet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    logger.error("getClientIp error", e);
                }
                ip = iNet.getHostAddress();
            }
        } catch (Exception e) {
            logger.error("RequestUtil ERROR ", e);
        }
//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}
        return ip;
    }

    /**
     * validate the ip
     *
     * @param ip ip
     * @return true or false
     */
    private static boolean isValidAddress(String ip) {
        if (ip == null) {
            return false;
        }
        for (int i = 0; i < ip.length(); ++i) {
            char ch = ip.charAt(i);
            if (ch >= '0' && ch <= '9') {
                // ignored
            } else if (ch >= 'A' && ch <= 'F') {
                // ignored
            } else if (ch >= 'a' && ch <= 'f') {
                // ignored
            } else if (ch == '.' || ch == ':') {
                //
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * obtain HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * obtain HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * obtain ServerRequestAttributes
     *
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获得所有请求参数
     *
     * @return Map
     */
    public static Map<String, String[]> getParams() {
        final Map<String, String[]> map = getRequest().getParameterMap();
        return Collections.unmodifiableMap(map);

    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return Map
     */
    public static Map<String, String[]> getParams(HttpServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获得所有请求参数
     *
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
     *
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

}
