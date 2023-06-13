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

import io.github.panxiaochao.operate.log.aop.OperateLogPointcutAdvisor;
import io.github.panxiaochao.operate.log.properties.OperateLogProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code OperateLogAutoConfiguration}
 * <p> description: OperateLog AutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-08
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OperateLogProperties.class)
@ConditionalOnWebApplication
public class OperateLogAutoConfiguration {

    @Bean
    public DefaultPointcutAdvisor doBefore() {
        return new OperateLogPointcutAdvisor().doBefore();
    }

    @Bean
    public DefaultPointcutAdvisor doAfterReturn() {
        return new OperateLogPointcutAdvisor().doAfterReturn();
    }

    @Bean
    public DefaultPointcutAdvisor doThrowing() {
        return new OperateLogPointcutAdvisor().doThrowing();
    }
}
