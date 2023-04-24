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
package io.github.panxiaochao.ip2region.config;

import io.github.panxiaochao.ip2region.template.Ip2regionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code Ip2regionAutoConfiguration}
 * <p> description: ip2region auto configuration
 *
 * @author Lypxc
 * @since 2023-04-24
 */
@Configuration(proxyBeanMethods = false)
public class Ip2regionAutoConfiguration {

    /**
     * Ip2region template类
     *
     * @return Ip2regionTemplate
     */
    @Bean
    public Ip2regionTemplate ip2regionTemplate() {
        return new Ip2regionTemplate();
    }
}
