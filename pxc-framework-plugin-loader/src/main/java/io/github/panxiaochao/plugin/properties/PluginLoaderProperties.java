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
package io.github.panxiaochao.plugin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code PluginLoaderProperties}
 * <p> description: 属性文件
 *
 * @author Lypxc
 * @since 2023-04-11
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = PluginLoaderProperties.PLUGIN_EXT_PREFIX, ignoreInvalidFields = true)
@ConditionalOnWebApplication
public class PluginLoaderProperties {

    /**
     * 属性前缀
     */
    public static final String PLUGIN_EXT_PREFIX = "plugin-ext";

    /**
     * 是否开启
     */
    private boolean enabled;

    /**
     * 插件存放路径
     */
    private String path;

    /**
     * 是否开启定时任务
     */
    private boolean scheduledEnabled;

    /**
     * 核心线程数
     */
    private int corePoolSize = 1;

    /**
     * 定时周期, 单位秒
     */
    private long scheduleTime = 300;

    /**
     * 延迟启动时间, 单位秒
     */
    private long scheduleDelay = 30;
}
