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
package io.github.panxiaochao.operate.log.config;

import io.github.panxiaochao.operate.log.context.MethodCostContext;
import io.github.panxiaochao.operate.log.utils.OperateLogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * {@code OperateLogAspectAutoConfiguration}
 * <p> description: Aspect AutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-20
 */
@Aspect
@AutoConfiguration
@ConditionalOnProperty(name = "spring.operatelog.enabled", havingValue = "true")
public class OperateLogAspectAutoConfiguration {

    /**
     * 排除自定义属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {};

    /**
     * 设置拦截切点
     */
    @Pointcut("@annotation(io.github.panxiaochao.operate.log.annotation.OperateLog) || @within(io.github.panxiaochao.operate.log.annotation.OperateLog)")
    public void operateLogPointCut() {
    }

    @Before("operateLogPointCut()")
    public void before(JoinPoint joinPoint) {
        // 设置请求方法执行开始时间
        MethodCostContext.setMethodCostTime(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "operateLogPointCut()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        OperateLogUtil.handleOperateLog(joinPoint, returnValue, null, EXCLUDE_PROPERTIES);
    }

    @AfterThrowing(pointcut = "operateLogPointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        OperateLogUtil.handleOperateLog(joinPoint, null, ex, EXCLUDE_PROPERTIES);
    }
}
