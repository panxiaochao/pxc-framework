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
package io.github.panxiaochao.operate.log.aop;

import io.github.panxiaochao.operate.log.context.MethodContext;
import io.github.panxiaochao.operate.log.domain.OperateLogDomain;
import io.github.panxiaochao.operate.log.utils.OperateLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * {@code OperateLogAdvice}
 * <p> description: OperateLogAdvice
 *
 * @author Lypxc
 * @since 2023-06-19
 */
public class OperateLogAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogAdvice.class);

    /**
     * 前置
     *
     * @param method the method being invoked
     * @param args   the arguments to the method
     * @param target the target of the method invocation. May be {@code null}.
     */
    @Override
    public void before(Method method, Object[] args, @Nullable Object target) {
        // 设置请求方法执行开始时间
        MethodContext.setMethodCostTime(System.currentTimeMillis());
    }

    /**
     * 后置
     *
     * @param returnValue the value returned by the method, if any
     * @param method      the method being invoked
     * @param args        the arguments to the method
     * @param target      the target of the method invocation. May be {@code null}.
     */
    @Override
    public void afterReturning(@Nullable Object returnValue, Method method, Object[] args, @Nullable Object target) {
        OperateLogDomain operateLogDomain = new OperateLogDomain();
        // 设置方法名称
        String className = target.getClass().getName();
        String methodName = method.getName();
        operateLogDomain.setClassMethod(className + "." + methodName + "()");
        // 处理其他字段
        OperateLogUtil.handleOperateLog(returnValue, method, args, operateLogDomain, null);
    }

    /**
     * 需要自定义实现 afterThrowing
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        LOGGER.error("AfterThrowing", ex);
        OperateLogDomain operateLogDomain = new OperateLogDomain();
        // 设置方法名称
        String className = target.getClass().getName();
        String methodName = method.getName();
        operateLogDomain.setClassMethod(className + "." + methodName + "()");
        // 处理其他字段
        OperateLogUtil.handleOperateLog(null, method, args, operateLogDomain, ex);
    }
}
