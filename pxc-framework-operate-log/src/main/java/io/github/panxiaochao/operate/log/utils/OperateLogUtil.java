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
import io.github.panxiaochao.operate.log.context.MethodCostContext;
import io.github.panxiaochao.operate.log.domain.OperateLogDomain;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
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
     * @param joinPoint         joinPoint
     * @param returnValue       返回值
     * @param ex                报错信息
     * @param excludeProperties 排除自定义属性字段
     */
    public static void handleOperateLog(final JoinPoint joinPoint, Object returnValue, Exception ex, String[] excludeProperties) {
        OperateLogDomain operateLogDomain = new OperateLogDomain();
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 参数
        Object[] args = joinPoint.getArgs();
        // Method
        Method method = methodSignature.getMethod();
        // 设置方法名称
        String className = target.getClass().getName();
        String methodName = method.getName();
        operateLogDomain.setClassName(target.getClass().getSimpleName());
        operateLogDomain.setClassMethod(className + "." + methodName + "()");
        // 获取注解 Annotation OperateLog.class
        OperateLog operateLog = obtainOperateLogAnnotation(method);
        operateLogDomain.setName(operateLog.name());
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
            setRequestParam(args, operateLogDomain, excludeProperties);
        }
        // 设置返回值
        if (operateLog.saveResData() && ObjectUtil.isNotEmpty(returnValue)) {
            operateLogDomain.setResponseData(StrUtil.substring(JacksonUtil.toString(returnValue), 0, 2000));
        }
        // 设置消耗时间
        operateLogDomain.setCostTime(System.currentTimeMillis() - MethodCostContext.getMethodCostTime());
        // 使用完清除，以免内存泄漏
        MethodCostContext.removeMethodCostTime();
        // 发布事件保存数据库
        SpringContextUtil.getInstance().publishEvent(operateLogDomain);
    }


    /**
     * 设置参数
     */
    private static void setRequestParam(Object[] args, OperateLogDomain operateLogDomain, String[] excludeProperties) {
        String requestMethod = operateLogDomain.getRequestMethod();
        Map<String, String> paramsMap = RequestUtil.getParamMap();
        if (HttpMethod.POST.name().equals(requestMethod) || HttpMethod.PUT.name().equals(requestMethod)) {
            String params = argsArrayToString(args, excludeProperties);
            if (StringUtils.hasText(params)) {
                operateLogDomain.setRequestBody(StrUtil.substring(params, 0, 2000));
            }
        }
        // 会出现混合模式，POST 中用跟参数的情况
        if (MapUtil.isNotEmpty(paramsMap)) {
            if (MapUtil.isNotEmpty(paramsMap)) {
                MapUtil.removeAny(paramsMap, excludeProperties);
            }
            operateLogDomain.setRequestParam(StrUtil.substring(JacksonUtil.toString(paramsMap), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private static String argsArrayToString(Object[] args, String[] excludeProperties) {
        StringBuilder params = new StringBuilder();
        if (ObjectUtil.isNotEmpty(args)) {
            for (Object object : args) {
                if (ObjectUtil.isNotEmpty(object) && !isFilterObject(object)) {
                    String jsonObj = JacksonUtil.toString(object);
                    // 排除自定义属性
                    if (!ArrayUtil.isEmpty(excludeProperties) && StrUtil.isNotBlank(jsonObj)) {
                        Map<String, Object> objectMap = JacksonUtil.toMap(jsonObj);
                        if (MapUtil.isNotEmpty(objectMap)) {
                            MapUtil.removeAny(objectMap, excludeProperties);
                            jsonObj = JacksonUtil.toString(objectMap);
                        }
                    }
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
            Collection<?> collection = (Collection<?>) o;
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
