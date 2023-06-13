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

import io.github.panxiaochao.operate.log.aop.afterreturn.OperateLogAfterReturningAdvice;
import io.github.panxiaochao.operate.log.aop.before.OperateLogMethodBeforeAdvice;
import io.github.panxiaochao.operate.log.aop.throwing.OperateLogThrowing;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * {@code OperateLogPointcutAdvisor}
 * <p> description: 自定义切点切面
 *
 * @author Lypxc
 * @since 2023-06-12
 */
@RequiredArgsConstructor
public class OperateLogPointcutAdvisor {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * <p>@annotation: 自定义注解标注
     * <p>@within: 自定义注解标注在的类上；该类的所有方法（不包含子类方法）
     */
    private static final String ANNOTATION_ACCESS_RATE_LIMIT =
            "@annotation(io.github.panxiaochao.operate.log.annotation.OperateLog) || " +
                    "@within(io.github.panxiaochao.operate.log.annotation.OperateLog)";

    /**
     * 前置切点
     *
     * @return DefaultPointcutAdvisor
     */
    public DefaultPointcutAdvisor doBefore() {
        DefaultPointcutAdvisor doBefore = new DefaultPointcutAdvisor();
        doBefore.setPointcut(buildPointcut());
        doBefore.setOrder(-1);
        doBefore.setAdvice(new OperateLogMethodBeforeAdvice());
        return doBefore;
    }

    /**
     * 后置返回切点
     *
     * @return DefaultPointcutAdvisor
     */
    public DefaultPointcutAdvisor doAfterReturn() {
        DefaultPointcutAdvisor doAfterReturn = new DefaultPointcutAdvisor();
        doAfterReturn.setPointcut(buildPointcut());
        doAfterReturn.setOrder(-1);
        doAfterReturn.setAdvice(new OperateLogAfterReturningAdvice());
        return doAfterReturn;
    }

    /**
     * 错误切点
     *
     * @return DefaultPointcutAdvisor
     */
    public DefaultPointcutAdvisor doThrowing() {
        DefaultPointcutAdvisor doAfterReturn = new DefaultPointcutAdvisor();
        doAfterReturn.setPointcut(buildPointcut());
        doAfterReturn.setOrder(-1);
        doAfterReturn.setAdvice(new OperateLogThrowing());
        return doAfterReturn;
    }

    /**
     * 组合切点
     *
     * @return Pointcut
     */
    private Pointcut buildPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(ANNOTATION_ACCESS_RATE_LIMIT);
        return pointcut;
    }
}
