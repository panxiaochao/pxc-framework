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

import io.github.panxiaochao.common.utils.RequestUtil;
import io.github.panxiaochao.redis.annotation.AccessRateLimit;
import io.github.panxiaochao.redis.enums.AccessRateLimitEnum;
import io.github.panxiaochao.redis.exception.AccessRateLimitException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@code AccessRateLimitAspectAutoConfiguration}
 * <p> description: AccessRateLimitAspectAutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-19
 */
@RequiredArgsConstructor
@Aspect
@AutoConfiguration
@ConditionalOnProperty(prefix = "spring.redis", name = "accessRateLimit", havingValue = "true")
public class AccessRateLimitAspectAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessRateLimitAspectAutoConfiguration.class);

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置拦截切点
     */
    @Pointcut("@annotation(io.github.panxiaochao.redis.annotation.AccessRateLimit) || @within(io.github.panxiaochao.redis.annotation.AccessRateLimit)")
    public void accessRateLimitPointCut() {
    }

    @Before("accessRateLimitPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AccessRateLimit accessRateLimit = null;
        // 得到访问的方法对象
        if (method.isAnnotationPresent(AccessRateLimit.class)) {
            // 1.采用最小化优先原则，先判断方法上是否有注解，优先方法注解
            accessRateLimit = method.getAnnotation(AccessRateLimit.class);
        } else if (method.getDeclaringClass().isAnnotationPresent(AccessRateLimit.class)) {
            // 2.判断类上是否有注解
            accessRateLimit = method.getDeclaringClass().getAnnotation(AccessRateLimit.class);
        }
        // 进行防刷限流拦截
        if (Objects.nonNull(accessRateLimit)) {
            String key = getAccessRateLimitKey(method, accessRateLimit);
            int maxCount = accessRateLimit.maxCount();
            int limitSecond = accessRateLimit.limitSecond();
            //
            Integer count = (Integer) redisTemplate.boundValueOps(key).get();
            // 第一次进入
            if (Objects.isNull(count)) {
                redisTemplate.boundValueOps(key).set(1, limitSecond, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                // 每访问一次接口更新一次对应的值，加1操作
                redisTemplate.boundValueOps(key).increment();
            } else {
                throw new AccessRateLimitException(AccessRateLimitEnum.ACCESS_RATE_LIMIT_FREQUENT_ERROR);
            }
        } else {
            LOGGER.error("Access rate limit is null");
        }
    }

    /**
     * 获取redis key
     *
     * @param method          method
     * @param accessRateLimit accessRateLimit
     * @return obtain the key
     */
    private String getAccessRateLimitKey(Method method, AccessRateLimit accessRateLimit) {
        String key = RequestUtil.ofRequestIp() + "." + method.getDeclaringClass().getName() + "." + method.getName();
        key = Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8));
        return accessRateLimit.key() + ":" + key;
    }
}
