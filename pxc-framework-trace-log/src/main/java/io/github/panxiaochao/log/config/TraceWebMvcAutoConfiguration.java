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
package io.github.panxiaochao.log.config;

import io.github.panxiaochao.log.interceptor.webmvc.TraceMvcConfigurer;
import io.github.panxiaochao.log.properties.TraceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * {@code TraceWebMvcAutoConfiguration}
 * <p> description: Spring WebMvc Configurer Log Configuration
 *
 * @author Lypxc
 * @since 2023-01-11
 */
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(name = {
        "org.springframework.web.servlet.config.annotation.WebMvcConfigurer",
        "org.springframework.boot.web.servlet.FilterRegistrationBean"
})
@EnableConfigurationProperties(TraceProperties.class)
public class TraceWebMvcAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceWebMvcAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(TraceMvcConfigurer.class)
    public TraceMvcConfigurer traceMvcConfigurer() {
        return new TraceMvcConfigurer();
    }

    @PostConstruct
    public void init() {
        LOGGER.info(">>> TraceWebMvc init");
    }
}
