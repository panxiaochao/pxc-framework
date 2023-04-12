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
        this.namePrefix = "pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        this.daemon = true;
        this.priority = Thread.NORM_PRIORITY;
    }

    public PluginLoaderThreadFactory(final String namePrefix) {
        this.namePrefix = namePrefix;
        this.daemon = true;
        this.priority = Thread.NORM_PRIORITY;
    }

    public PluginLoaderThreadFactory(final String namePrefix, final boolean daemon) {
        this.namePrefix = namePrefix;
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
