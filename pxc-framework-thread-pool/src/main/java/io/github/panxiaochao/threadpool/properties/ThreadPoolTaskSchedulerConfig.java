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
public class ThreadPoolTaskSchedulerConfig {

    /**
     * 核心线程数
     */
    private int poolSize = 5;

    /**
     * 调度器shutdown被调用时等待当前被调度的任务完成
     */
    private boolean waitForTasksToCompleteOnShutdown = true;

    /**
     * 等待终止时间，单位秒，默认60
     */
    private int awaitTerminationSeconds = 60;

    /**
     * 线程前缀
     */
    private String threadNamePrefix = "pxc-scheduler-thread-pool-";

    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
}
