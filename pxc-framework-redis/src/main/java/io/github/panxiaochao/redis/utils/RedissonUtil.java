package io.github.panxiaochao.redis.utils;

import io.github.panxiaochao.common.utils.SpringContextUtil;
import io.github.panxiaochao.redis.function.RLockTryFail;
import io.github.panxiaochao.redis.function.RLockTrySuccess;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * {@code RedissonUtil}
 * <p> description: Redisson tools
 *
 * @author Lypxc
 * @since 2023-06-14
 */
public class RedissonUtil {

    /**
     * LOGGER RedissonUtil.class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonUtil.class);

    private RedissonUtil() {
    }

    /**
     * obtain Redisson client
     */
    private final RedissonClient redissonClient = SpringContextUtil.getInstance().getBean(RedissonClient.class);

    /**
     * 初始化
     */
    private static final RedissonUtil REDISSON_UTIL = new RedissonUtil();

    /**
     * obtain static RedissonUtil instance
     *
     * @return RedissonUtil
     */
    public static RedissonUtil INST() {
        return REDISSON_UTIL;
    }

    /**
     * obtain RLock
     *
     * @param lockName the lock name
     * @return RLock object
     */
    public RLock rLock(String lockName) {
        return redissonClient.getLock(lockName);
    }

    /**
     * tryLock by lockName
     *
     * @param lockName  the lock name
     * @param waitTime  the maximum time to acquire the lock
     * @param leaseTime lease time
     * @param unit      time unit
     * @return <code>true</code> if lock is successfully acquired,
     * otherwise <code>false</code> if lock is already set.
     */
    public boolean tryLock(String lockName, long waitTime, long leaseTime, TimeUnit unit) {
        boolean tryLockSuccess = false;
        try {
            tryLockSuccess = rLock(lockName).tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            LOGGER.error("Exception tryLock", e);
        }
        return tryLockSuccess;
    }

    /**
     * tryLock by RLock
     *
     * @param lock      the RLock object
     * @param waitTime  the maximum time to acquire the lock
     * @param leaseTime lease time
     * @param unit      time unit
     * @return <code>true</code> if lock is successfully acquired,
     * otherwise <code>false</code> if lock is already set.
     */
    public boolean tryLock(RLock lock, long waitTime, long leaseTime, TimeUnit unit) {
        boolean tryLockSuccess = false;
        try {
            tryLockSuccess = lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            LOGGER.error("Exception tryLock", e);
        }
        return tryLockSuccess;
    }

    /**
     * Releases the lock
     *
     * @param lock the RLock Object
     */
    public void unLock(RLock lock) {
        // 是否上锁 && 是否同一个线程
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    /**
     * tryLock by lockName
     *
     * @param lockName        the lock name
     * @param waitTime        the maximum time to acquire the lock
     * @param leaseTime       lease time
     * @param unit            time unit
     * @param rLockTrySuccess the customize lock success handle
     * @param rLockTryFail    the customize lock error handle
     */
    public void tryLock(String lockName, long waitTime, long leaseTime, TimeUnit unit, RLockTrySuccess rLockTrySuccess, RLockTryFail rLockTryFail) {
        boolean tryLockSuccess;
        RLock rLock = rLock(lockName);
        try {
            tryLockSuccess = rLock.tryLock(waitTime, leaseTime, unit);
            if (tryLockSuccess) {
                rLockTrySuccess.successHandle();
            } else {
                rLockTryFail.errorHandle();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Exception tryLock", e);
        } finally {
            unLock(rLock);
        }
    }
}
