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
package io.github.panxiaochao.threadpool.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * {@code ThreadPoolConfig}
 * <p> ThreadPool 配置文件
 *
 * @author Lypxc
 * @since 2022/11/7
 */
@Getter
@Setter
@ConfigurationProperties(prefix = ThreadPoolProperties.THREAD_POOL_PREFIX, ignoreInvalidFields = true)
public class ThreadPoolProperties {

    /**
     * 属性前缀
     */
    public static final String THREAD_POOL_PREFIX = "spring.thread";

    /**
     * 是否开启多线程
     */
    private boolean enabled;

    /**
     * 多线程任务配置
     */
    @NestedConfigurationProperty
    private ThreadPoolTaskConfig threadPoolTask = new ThreadPoolTaskConfig();

    /**
     * 定时任务多线程任务配置
     */
    @NestedConfigurationProperty
    private ThreadPoolTaskSchedulerConfig threadPoolTaskScheduler = new ThreadPoolTaskSchedulerConfig();
}
