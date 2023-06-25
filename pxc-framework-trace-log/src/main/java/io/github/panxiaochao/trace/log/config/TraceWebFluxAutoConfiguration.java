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
package io.github.panxiaochao.trace.log.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * {@code TraceWebFluxAuoConfiguration}
 * <p> description: Spring WebFlux Configurer Log Configuration
 *
 * @author Lypxc
 * @since 2023-01-11
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class TraceWebFluxAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceWebFluxAutoConfiguration.class);

    @PostConstruct
    public void init() {
        LOGGER.info(">>> TraceWebFlux init");
    }
}
