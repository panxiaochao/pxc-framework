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
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * {@code SpringDocInfo}
 * <p> SpringDoc 基本信息
 *
 * @author Lypxc
 * @since 2022/10/24
 */
@Getter
@Setter
@ToString
public class SpringDocInfo {

    /**
     * 标题
     */
    private String title = "SpringDoc Default API";

    /**
     * 描述
     */
    private String description = "SpringDoc Default API Description";

    /**
     * 服务条款
     */
    private String termsOfService = "/";

    /**
     * 联系人
     */
    @NestedConfigurationProperty
    private SpringDocContact contact = new SpringDocContact();

    /**
     * 许可证
     */
    @NestedConfigurationProperty
    private SpringDocLicense license = new SpringDocLicense();

    /**
     * 版本号
     */
    private String version = "V1.0.0";
}
