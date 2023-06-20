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

import io.github.panxiaochao.common.abstracts.CustomizeAnnotationPointAdvisor;
import io.github.panxiaochao.operate.log.annotation.OperateLog;
import io.github.panxiaochao.operate.log.aop.OperateLogAdvice;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * {@code OperateLogAutoConfiguration}
 * <p> description: OperateLog AutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-08
 */
@Configuration
@ConditionalOnProperty(name = "spring.operatelog.enabled", havingValue = "true")
@ConditionalOnWebApplication
public class OperateLogAutoConfiguration {

    /**
     * OperateLogAdvisor 切面+切点
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public Advisor operateLogAdvisor() {
        CustomizeAnnotationPointAdvisor advisor =
                new CustomizeAnnotationPointAdvisor(new OperateLogAdvice(), OperateLog.class);
        advisor.setOrder(-1);
        return advisor;
    }
}
