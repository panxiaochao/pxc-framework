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
package io.github.panxiaochao.plugin.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@code PluginLoaderThreadFactory}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-12
 */
public class PluginLoaderThreadFactory implements ThreadFactory {

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("Pxc-Plugin-Group");

    private final boolean daemon;

    private final String namePrefix;

    private final int priority;

    public PluginLoaderThreadFactory() {
        this.namePrefix = "pool-plugin-loader-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        this.daemon = true;
        this.priority = Thread.NORM_PRIORITY;
    }

    public PluginLoaderThreadFactory(final String namePrefix) {
        this.namePrefix = "pool-" + namePrefix + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        this.daemon = true;
        this.priority = Thread.NORM_PRIORITY;
    }

    public PluginLoaderThreadFactory(final String namePrefix, final boolean daemon) {
        this.namePrefix = "pool-" + namePrefix + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        this.daemon = daemon;
        this.priority = Thread.NORM_PRIORITY;
    }

    public PluginLoaderThreadFactory(final String namePrefix, final boolean daemon, final int priority) {
        this.namePrefix = namePrefix;
        this.daemon = daemon;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r,
                THREAD_GROUP.getName() + "-" + namePrefix + "-" + THREAD_NUMBER.getAndIncrement());
        thread.setDaemon(daemon);
        thread.setPriority(priority);
        return thread;
    }
}
