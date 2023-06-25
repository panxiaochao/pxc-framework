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
package io.github.panxiaochao.trace.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code TraceProperties}
 * <p> description: 属性文件
 *
 * @author Lypxc
 * @since 2023-01-18
 */
@Getter
@Setter
@ConfigurationProperties(prefix = TraceProperties.PXC_TRACE_PREFIX, ignoreInvalidFields = true)
public class TraceProperties {

    /**
     * 属性前缀
     */
    public static final String PXC_TRACE_PREFIX = "pxc.trace";

    /**
     * 是否开启
     */
    private boolean enabled;
}
