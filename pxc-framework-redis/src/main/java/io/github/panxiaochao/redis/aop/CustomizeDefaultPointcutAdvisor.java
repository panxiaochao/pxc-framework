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
package io.github.panxiaochao.redis.aop;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * s
 * {@code CustomizeDefaultPointcutAdvisor}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-05-26
 */
@RequiredArgsConstructor
public class CustomizeDefaultPointcutAdvisor {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * <p>@annotation: 自定义注解标注
     * <p>@within: 自定义注解标注在的类上；该类的所有方法（不包含子类方法）
     */
    private static final String ANNOTATION_ACCESS_RATE_LIMIT =
            "@annotation(io.github.panxiaochao.redis.annotation.AccessRateLimit) || " +
                    "@within(io.github.panxiaochao.redis.annotation.AccessRateLimit)";

    /**
     * 前置环绕
     *
     * @return DefaultPointcutAdvisor
     */
    public DefaultPointcutAdvisor doBefore() {
        LOGGER.info(">>> AccessRateLimitMethodBefore");
        DefaultPointcutAdvisor doBefore = new DefaultPointcutAdvisor();
        doBefore.setPointcut(buildPointcut());
        doBefore.setOrder(-1);
        doBefore.setAdvice(new AccessRateLimitMethodBefore(redisTemplate));
        return doBefore;
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
