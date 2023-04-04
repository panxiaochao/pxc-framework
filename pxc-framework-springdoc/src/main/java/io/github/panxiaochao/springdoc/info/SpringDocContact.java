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
import lombok.ToString;

import java.util.Map;

/**
 * {@code SpringDocContact}
 * <p>
 *
 * @author HzYaTop
 * @since 2022/10/24
 */
@Getter
@Setter
@ToString
public class SpringDocContact {

    /**
     * 联系人名
     */
    private String name = "Lypxc";

    /**
     * 联系人地址
     */
    private String url;

    /**
     * 联系人邮箱
     */
    private String email;

    /**
     * 扩展属性
     */
    private Map<String, Object> extensions;
}
