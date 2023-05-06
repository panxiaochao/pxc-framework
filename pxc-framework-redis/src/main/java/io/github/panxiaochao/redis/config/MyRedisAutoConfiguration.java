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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import io.github.panxiaochao.redis.service.IRedisService;
import io.github.panxiaochao.redis.service.impl.RedisServiceImpl;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * {@code RedisConfig}
 * <p> description: Redis Config
 *
 * @author Lypxc
 * @since 2022-12-06
 */
@Configuration
@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration")
@ConditionalOnProperty(prefix = "spring.redis")
@ConditionalOnWebApplication
public class MyRedisAutoConfiguration {

    private static final String STRING_EMPTY = "";

    /**
     * 单实例, 返回 RedissonClient
     *
     * @return RedissonClient
     */
    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(ObjectProvider<RedisProperties> redisPropertiesObjectProvider) {
        RedisProperties redisProperties = redisPropertiesObjectProvider.getIfAvailable();
        Objects.requireNonNull(redisProperties, () -> "spring.redis properties is null!");
        String address = String.join(STRING_EMPTY, "redis://", redisProperties.getHost(), ":" + redisProperties.getPort());
        Config config = new Config();
        config.useSingleServer()
                .setAddress(address)
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());
        return Redisson.create(config);
    }

    /**
     * Redis 序列化配置
     * 采用 RedissonConnectionFactory 工厂
     *
     * @param redissonClient redissonClient
     * @return RedisTemplate
     */
    @Bean("redisTemplate")
    @ConditionalOnBean(RedissonClient.class)
    public RedisTemplate<Object, Object> redisTemplate(RedissonClient redissonClient) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        RedissonConnectionFactory redissonConnectionFactory = new RedissonConnectionFactory(redissonClient);
        template.setConnectionFactory(redissonConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public IRedisService redisService(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisServiceImpl(redisTemplate);
    }
}
