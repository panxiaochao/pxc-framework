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
package io.github.panxiaochao.springdoc.info;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * {@code SpringSecurityScheme}
 * <p> description: 自定义Auth HeaderName
 *
 * @author Lypxc
 * @since 2023-03-06
 */
@Getter
@Setter
public class SpringSecurityScheme {

    /**
     * 多个headerName
     */
    private List<Config> configs;

    @Getter
    @Setter
    public static class Config {
        /**
         * headerName, 例如：AccessToken
         */
        private String headerName;
    }
}
