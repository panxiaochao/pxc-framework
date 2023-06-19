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
package io.github.panxiaochao.redis.config;

import io.github.panxiaochao.common.abstracts.CustomizeAnnotationPointAdvisor;
import io.github.panxiaochao.redis.annotation.AccessRateLimit;
import io.github.panxiaochao.redis.aop.AccessRateLimitAdvice;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * {@code AccessRateLimitConfiguration}
 * <p> description: AccessRateLimitConfiguration
 *
 * @author Lypxc
 * @since 2023-06-19
 */
@Configuration
public class AccessRateLimitConfiguration {

    /**
     * AccessRateLimit 切面+切点
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    @ConditionalOnProperty(prefix = "spring.redis", name = "accessRateLimit", havingValue = "true")
    public Advisor accessRateLimitPointcutAdvisor(ObjectProvider<RedisTemplate<String, Object>> redisTemplateObjectProvider) {
        RedisTemplate<String, Object> redisTemplate = redisTemplateObjectProvider.getIfAvailable();
        CustomizeAnnotationPointAdvisor advisor =
                new CustomizeAnnotationPointAdvisor(new AccessRateLimitAdvice(redisTemplate), AccessRateLimit.class);
        advisor.setOrder(-1);
        return advisor;
    }
}
