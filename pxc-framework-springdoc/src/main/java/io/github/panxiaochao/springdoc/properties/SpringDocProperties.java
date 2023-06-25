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
package io.github.panxiaochao.springdoc.properties;

import io.github.panxiaochao.springdoc.info.SpringDocInfo;
import io.github.panxiaochao.springdoc.info.SpringSecurityScheme;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * {@code SpringDocProperties}
 * <p> SpringDoc 属性文件
 *
 * @author Lypxc
 * @since 2022-10-23
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = SpringDocProperties.SPRING_DOC_PREFIX, ignoreInvalidFields = true)
public class SpringDocProperties {

    /**
     * 属性前缀
     */
    public static final String SPRING_DOC_PREFIX = "springdoc";

    /**
     * 是否开启
     */
    private boolean enabled;

    /**
     * 基本信息
     **/
    @NestedConfigurationProperty
    private SpringDocInfo info = new SpringDocInfo();

    /**
     * 自定义Auth HeaderName, 例如：AccessToken
     **/
    @NestedConfigurationProperty
    private SpringSecurityScheme securityScheme = new SpringSecurityScheme();
}
