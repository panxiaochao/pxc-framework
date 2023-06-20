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
package io.github.panxiaochao.web.config;

import io.github.panxiaochao.common.handler.RestExceptionHandler;
import io.github.panxiaochao.common.handler.RestResponseEntityExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * {@code WebMvcAutoConfiguration}
 * <p> description: WebMvcAutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-20
 */
@AutoConfiguration
@Import({WebMvcConfiguration.class, FilterConfiguration.class, SpringApplicationContextConfiguration.class})
public class WebMvcAutoConfiguration {

    /**
     * 统一拦截增强
     *
     * @return RestExceptionHandler
     */
    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }

    /**
     * 统一拦截增强
     *
     * @return RestResponseEntityExceptionHandler
     */
    @Bean
    public RestResponseEntityExceptionHandler restResponseEntityExceptionHandler() {
        return new RestResponseEntityExceptionHandler();
    }
}
