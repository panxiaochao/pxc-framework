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
package io.github.panxiaochao.operate.log.properties;

import io.github.panxiaochao.operate.log.enums.OperateLogType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code OperateLogProperties}
 * <p> description: OperateLogProperties
 *
 * @author Lypxc
 * @since 2023-06-20
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "spring.operatelog", ignoreInvalidFields = true)
public class OperateLogProperties {

    /**
     * 是否开启
     */
    public boolean enabled;

    /**
     * 存储日志类型
     */
    public OperateLogType logType = OperateLogType.LOGGER;
}
