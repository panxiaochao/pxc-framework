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
package io.github.panxiaochao.common.abstracts;

import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * {@code CustomizeAnnotationPointAdvisor}
 * <p> description: 自定义注解 切面+切点
 *
 * @author Lypxc
 * @since 2023-06-19
 */
@RequiredArgsConstructor
public class CustomizeAnnotationPointAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private static final long serialVersionUID = -548702738874501421L;

    private final Advice advice;

    private final Pointcut pointcut;

    private final Class<? extends Annotation> annotation;

    public CustomizeAnnotationPointAdvisor(@NonNull Advice advice,
                                           @NonNull Class<? extends Annotation> annotation) {
        this.advice = advice;
        this.annotation = annotation;
        this.pointcut = buildPointcut();
    }

    /**
     * Get the Pointcut that drives this advisor.
     */
    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     *
     * @return the advice that should apply if the pointcut matches
     * @see MethodInterceptor
     * @see BeforeAdvice
     * @see ThrowsAdvice
     * @see AfterReturningAdvice
     */
    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    /**
     * 组合切点
     *
     * @return Pointcut
     */
    private Pointcut buildPointcut() {
        Pointcut cpc = new AnnotationMatchingPointcut(annotation, true);
        Pointcut mpc = new AnnotationMatchingPointcut(null, annotation, true);
        return new ComposablePointcut(cpc).union(mpc);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
}
