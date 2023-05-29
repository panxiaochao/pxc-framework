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

import io.github.panxiaochao.common.utils.RequestUtil;
import io.github.panxiaochao.redis.annotation.AccessRateLimit;
import io.github.panxiaochao.redis.enums.AccessRateLimitEnum;
import io.github.panxiaochao.redis.exception.AccessRateLimitException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@code AccessRateLimitMethodBefore}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-05-26
 */
@RequiredArgsConstructor
public class AccessRateLimitMethodBefore implements MethodBeforeAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessRateLimitMethodBefore.class);

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void before(Method method, Object[] args, @Nullable Object target) {
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
            LOGGER.info("count:{}", count);
            // 第一次进入
            if (Objects.isNull(count)) {
                LOGGER.info("第一次进入");
                redisTemplate.boundValueOps(key).set(1, limitSecond, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                LOGGER.info("允许访问");
                // 每访问一次接口更新一次对应的值，加1操作
                redisTemplate.boundValueOps(key).increment();
            } else {
                LOGGER.info("超出限制了");
                throw new AccessRateLimitException(AccessRateLimitEnum.ACCESS_RATE_LIMIT_FREQUENT_ERROR);
            }
        } else {
            LOGGER.info("Access rate limit is null");
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
        String key = method.getDeclaringClass().getName() + "." + method.getName();
        key = Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8));
        // LOGGER.info("key: {}", Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8)));
        return accessRateLimit.key() + RequestUtil.ofRequestIp() + ":" + key;
    }
}
