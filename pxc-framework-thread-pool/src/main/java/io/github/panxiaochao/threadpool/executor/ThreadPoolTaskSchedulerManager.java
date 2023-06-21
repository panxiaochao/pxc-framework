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
package io.github.panxiaochao.threadpool.executor;

import io.github.panxiaochao.threadpool.properties.ThreadPoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * {@code ThreadPoolTaskSchedulerManager}
 * <p> 自定义多线程执行
 *
 * @author Lypxc
 * @since 2022/11/7
 */
public class ThreadPoolTaskSchedulerManager extends ThreadPoolTaskScheduler {

    private static final long serialVersionUID = -3482428057958160591L;

    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTaskSchedulerManager.class);

    /**
     * 定时任务多线程
     *
     * @param threadPoolProperties 属性配置
     */
    public ThreadPoolTaskSchedulerManager(ThreadPoolProperties threadPoolProperties) {
        // 核心线程数目
        super.setPoolSize(threadPoolProperties.getScheduler().getPoolSize());
        // 调度器shutdown被调用时等待当前被调度的任务完成
        super.setAwaitTerminationSeconds(threadPoolProperties.getScheduler().getAwaitTerminationSeconds());
        // 线程名称前缀
        super.setThreadNamePrefix(threadPoolProperties.getScheduler().getThreadNamePrefix());
        // 线程名称前缀
        super.setWaitForTasksToCompleteOnShutdown(threadPoolProperties.getScheduler().isWaitForTasksToCompleteOnShutdown());
        // 线程拒绝策略
        super.setRejectedExecutionHandler(threadPoolProperties.getScheduler().getRejectedExecutionHandler());
        // 初始化
        super.initialize();
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        printThreadPoolInfo();
        return super.schedule(task, trigger);
    }

    /**
     * @param taskName 任务名称
     * @param task     执行任务
     * @param trigger  触发条件
     * @return ScheduledFuture
     */
    public ScheduledFuture<?> schedule(String taskName, Runnable task, Trigger trigger) {
        printThreadPoolInfo(taskName);
        return super.schedule(task, trigger);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        printThreadPoolInfo();
        return super.schedule(task, startTime);
    }

    /**
     * @param taskName  任务名称
     * @param task      执行任务
     * @param startTime 开始时间
     * @return ScheduledFuture
     */
    public ScheduledFuture<?> schedule(String taskName, Runnable task, Date startTime) {
        printThreadPoolInfo(taskName);
        return super.schedule(task, startTime);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        printThreadPoolInfo();
        return super.scheduleAtFixedRate(task, startTime, period);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        printThreadPoolInfo();
        return super.scheduleAtFixedRate(task, period);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        printThreadPoolInfo();
        return super.scheduleWithFixedDelay(task, startTime, delay);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        printThreadPoolInfo();
        return super.scheduleWithFixedDelay(task, delay);
    }

    @Override
    public void execute(Runnable task) {
        printThreadPoolInfo();
        super.execute(task);
    }

    /**
     * @param taskName 任务名称
     * @param task     执行任务
     */
    public void execute(String taskName, Runnable task) {
        printThreadPoolInfo(taskName);
        super.execute(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        printThreadPoolInfo();
        return super.submit(task);
    }

    /**
     * @param taskName 任务名称
     * @param task     执行任务
     * @return Future
     */
    public Future<?> submit(String taskName, Runnable task) {
        printThreadPoolInfo(taskName);
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        printThreadPoolInfo();
        return super.submit(task);
    }

    /**
     * @param taskName 任务名称
     * @param task     执行任务
     * @return Future
     */
    public <T> Future<T> submit(String taskName, Callable<T> task) {
        printThreadPoolInfo(taskName);
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        printThreadPoolInfo();
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        printThreadPoolInfo();
        return super.submitListenable(task);
    }

    @Override
    protected void cancelRemainingTask(Runnable task) {
        printThreadPoolInfo();
        super.cancelRemainingTask(task);
    }

    /**
     * 打印线程信息
     */
    private void printThreadPoolInfo() {
        printThreadPoolInfo("");
    }


    /**
     * @param taskName 任务名
     */
    private void printThreadPoolInfo(String taskName) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = super.getScheduledThreadPoolExecutor();
        if (Objects.isNull(scheduledThreadPoolExecutor)) {
            return;
        }
        LOGGER.info("{}, taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                super.getThreadNamePrefix() + taskName,
                scheduledThreadPoolExecutor.getTaskCount(),
                scheduledThreadPoolExecutor.getCompletedTaskCount(),
                scheduledThreadPoolExecutor.getActiveCount(),
                scheduledThreadPoolExecutor.getQueue().size());
    }
}
