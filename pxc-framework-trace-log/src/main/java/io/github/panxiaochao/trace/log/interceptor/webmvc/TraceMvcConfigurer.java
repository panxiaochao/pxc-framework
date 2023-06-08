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
package io.github.panxiaochao.trace.log.interceptor.webmvc;

import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * {@code TraceMvcConfigurer}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-01-17
 */
public class TraceMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration =
                registry.addInterceptor(new TraceWebMvcInterceptor()).addPathPatterns("/**");
        // 这里是为了兼容springboot 1.5.X 没有order这个方法
        try {
            Method method = ReflectionUtils.findMethod(InterceptorRegistration.class, "order", Integer.class);
            if (Objects.nonNull(method)) {
                method.invoke(interceptorRegistration, Ordered.HIGHEST_PRECEDENCE);
            }
        } catch (Exception e) {
            // skip
        }
    }
}
