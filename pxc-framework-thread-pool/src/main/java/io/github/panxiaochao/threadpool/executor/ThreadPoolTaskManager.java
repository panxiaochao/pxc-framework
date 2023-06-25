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

import io.github.panxiaochao.threadpool.decorator.TraceCopyContextTaskDecorator;
import io.github.panxiaochao.threadpool.properties.ThreadPoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code ThreadPoolTaskManager}
 * <p> 自定义多线程执行
 *
 * @author Lypxc
 * @since 2022/11/7
 */
public class ThreadPoolTaskManager extends ThreadPoolTaskExecutor {

    private static final long serialVersionUID = 2037357330354020324L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTaskManager.class);

    /**
     * 构造 多线程任务配置：
     * <pre>
     * ThreadPoolTaskExecutor用法：
     * corePoolSize：线程池维护线程最小的数量，默认为1
     * maxPoolSize：线程池维护线程最大数量，默认为Integer.MAX_VALUE
     * keepAliveSeconds：(maxPoolSize-corePoolSize)部分线程空闲最大存活时间，默认存活时间是60s
     * queueCapacity：阻塞任务队列的大小，默认为Integer.MAX_VALUE，默认使用LinkedBlockingQueue
     * allowCoreThreadTimeOut：设置为true的话，keepAliveSeconds参数设置的有效时间对corePoolSize线程也有效，默认是flase
     * threadFactory：：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字。使用开源框架guava提供的ThreadFactoryBuilder可以快速给线程池里的线程设置有意义的名字
     * rejectedExecutionHandler：拒绝策略，当队列workQueue和线程池maxPoolSize都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常，有以下四种策略，当然也可以根据实际业务需求类实现RejectedExecutionHandler接口实现自己的处理策略
     * 1.AbortPolicy：丢弃任务，并且抛出RejectedExecutionException异常
     * 2.DiscardPolicy：丢弃任务，不处理，不抛出异常
     * 3.CallerRunsPolicy：直接在execute方法的调用线程中运行被拒绝的任务
     * 4.DiscardOldestPolicy：丢弃队列中最前面的任务，然后重新尝试执行任务
     * </pre>
     *
     * @param threadPoolProperties 属性配置
     */
    public ThreadPoolTaskManager(ThreadPoolProperties threadPoolProperties) {
        // 核心线程数
        super.setCorePoolSize(threadPoolProperties.getThreadpool().getCorePoolSize());
        // 最大线程数
        super.setMaxPoolSize(threadPoolProperties.getThreadpool().getMaxPoolSize());
        // 队列大小
        super.setQueueCapacity(threadPoolProperties.getThreadpool().getQueueCapacity());
        // 线程活跃时间(秒)
        super.setKeepAliveSeconds(threadPoolProperties.getThreadpool().getKeepAliveSeconds());
        // 线程前缀
        super.setThreadNamePrefix(threadPoolProperties.getThreadpool().getThreadNamePrefix());
        // 线程分组名称
        super.setThreadGroupName(threadPoolProperties.getThreadpool().getThreadGroupName());
        // 所有任务结束后关闭线程池
        super.setWaitForTasksToCompleteOnShutdown(threadPoolProperties.getThreadpool().isWaitForJobsToCompleteOnShutdown());
        // 现场拒绝策略
        super.setRejectedExecutionHandler(threadPoolProperties.getThreadpool().getRejectedExecutionHandler());
        // 添加装饰器，上文传递
        super.setTaskDecorator(new TraceCopyContextTaskDecorator());
        // 初始化
        super.initialize();
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
     * 打印线程信息
     */
    private void printThreadPoolInfo(String taskName) {
        ThreadPoolExecutor threadPoolExecutor = super.getThreadPoolExecutor();
        if (Objects.isNull(threadPoolExecutor)) {
            return;
        }
        LOGGER.info("{}, taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                super.getThreadNamePrefix() + taskName,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }
}
