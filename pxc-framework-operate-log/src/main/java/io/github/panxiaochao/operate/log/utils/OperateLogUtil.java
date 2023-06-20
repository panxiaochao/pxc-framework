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
package io.github.panxiaochao.operate.log.utils;

import io.github.panxiaochao.common.utils.*;
import io.github.panxiaochao.operate.log.annotation.OperateLog;
import io.github.panxiaochao.operate.log.context.MethodContext;
import io.github.panxiaochao.operate.log.domain.OperateLogDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * {@code OperateLogUtil}
 * <p> description: 工具类
 *
 * @author Lypxc
 * @since 2023-06-12
 */
public class OperateLogUtil {

    /**
     * LOGGER OperateLogUtil.class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogUtil.class);

    /**
     * obtain OperateLog Annotation
     *
     * @param method method
     * @return obtain OperateLog Annotation
     */
    public static OperateLog obtainOperateLogAnnotation(Method method) {
        OperateLog operateLog = null;
        if (method.isAnnotationPresent(OperateLog.class)) {
            // 1.采用最小化优先原则，先判断方法上是否有注解，优先方法注解
            operateLog = method.getAnnotation(OperateLog.class);
        } else {
            // 2.判断类上是否有注解
            Class<?> declaringClass = method.getDeclaringClass();
            if (declaringClass.isAnnotationPresent(OperateLog.class)) {
                operateLog = declaringClass.getAnnotation(OperateLog.class);
            }
        }
        return operateLog;
    }

    /**
     * 处理日志方式
     *
     * @param returnValue      返回值
     * @param method           方法映射头
     * @param args             方法参数
     * @param operateLogDomain 日志领域
     */
    public static void handleOperateLog(Object returnValue, Method method, Object[] args, OperateLogDomain operateLogDomain, Exception ex) {
        OperateLog operateLog = OperateLogUtil.obtainOperateLogAnnotation(method);
        operateLogDomain.setModule(operateLog.module());
        operateLogDomain.setDescription(operateLog.description());
        operateLogDomain.setPattern(operateLog.pattern());
        operateLogDomain.setBusinessType(operateLog.businessType().ordinal());
        operateLogDomain.setOperateUsertype(operateLog.operatorUserType().ordinal());
        operateLogDomain.setRequestUrl(RequestUtil.getRequest().getRequestURI());
        operateLogDomain.setRequestMethod(RequestUtil.getRequest().getMethod());
        operateLogDomain.setRequestContentType(RequestUtil.getRequest().getContentType());
        operateLogDomain.setIp(RequestUtil.ofRequestIp());
        operateLogDomain.setRequestDateTime(LocalDateTime.now());
        if (ex != null) {
            operateLogDomain.setSuccess(0);
            operateLogDomain.setErrorMessage(StrUtil.substring(ExceptionUtil.getMessage(ex), 0, 2000));
        } else {
            operateLogDomain.setSuccess(1);
        }
        // 设置请求参数
        if (operateLog.saveReqParams()) {
            setRequestParam(args, operateLogDomain);
        }
        // 设置返回值
        if (operateLog.saveResData() && ObjectUtil.isNotEmpty(returnValue)) {
            operateLogDomain.setResponseData(StrUtil.substring(JacksonUtil.toString(returnValue), 0, 2000));
        }
        // 设置消耗时间
        operateLogDomain.setCostTime(System.currentTimeMillis() - MethodContext.getMethodCostTime());
        // TODO 保存数据库 OR 日志存储
        LOGGER.info("OperateLog logging: {} ", JacksonUtil.toString(operateLogDomain));
    }


    /**
     * 设置参数
     */
    private static void setRequestParam(Object[] args, OperateLogDomain operateLogDomain) {
        String requestMethod = operateLogDomain.getRequestMethod();
        Map<String, String> paramsMap = RequestUtil.getParamMap();
        if (HttpMethod.POST.name().equals(requestMethod) || HttpMethod.PUT.name().equals(requestMethod)) {
            String params = argsArrayToString(args);
            if (StringUtils.hasText(params)) {
                operateLogDomain.setRequestBody(StrUtil.substring(params, 0, 2000));
            }
        }
        // 会出现混合模式，POST 中用跟参数的情况
        if (MapUtil.isNotEmpty(paramsMap)) {
            operateLogDomain.setRequestParam(StrUtil.substring(JacksonUtil.toString(paramsMap), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private static String argsArrayToString(Object[] args) {
        StringBuilder params = new StringBuilder();
        if (ObjectUtil.isNotEmpty(args)) {
            for (Object object : args) {
                // 附件类型
                // if (object instanceof MultipartFile) {
                //     FileObjectInfo fileObjectInfo = new FileObjectInfo((MultipartFile) object);
                //     params.append(JacksonUtil.toString(fileObjectInfo)).append(" ");
                // } else
                if (ObjectUtil.isNotEmpty(object) && !isFilterObject(object)) {
                    String jsonObj = JacksonUtil.toString(object);
                    params.append(jsonObj).append(" ");
                }
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    private static boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return (o instanceof MultipartFile ||
                o instanceof HttpServletRequest ||
                o instanceof HttpServletResponse ||
                o instanceof BindingResult);
    }
}
