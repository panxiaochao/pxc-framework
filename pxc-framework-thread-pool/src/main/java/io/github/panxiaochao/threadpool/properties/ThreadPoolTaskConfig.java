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

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code ThreadPoolTaskConfig}
 * <p>
 *
 * @author Lypxc
 * @since 2022/11/7
 */
@Getter
@Setter
public class ThreadPoolTaskConfig {

    /**
     * 核心线程数
     */
    private int corePoolSize = 5;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 10;

    /**
     * 线程活跃时间，单位秒
     */
    private int keepAliveSeconds = 60;

    /**
     * 队列大小
     */
    private int queueCapacity = 100;

    /**
     * 设置为true的话，keepAliveSeconds参数设置的有效时间对corePoolSize线程也有效，默认是false
     */
    private boolean allowCoreThreadTimeOut = false;

    /**
     * 所有任务结束后关闭线程池
     */
    private boolean waitForJobsToCompleteOnShutdown = true;

    /**
     * 等待终止时间，单位秒，默认60
     */
    private int awaitTerminationSeconds = 60;

    /**
     * 线程前缀
     */
    private String threadNamePrefix = "pxc-thread-pool-";

    /**
     * 线程分组名称
     */
    private String threadGroupName = "pxc-group-thread-pool-";

    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
}
