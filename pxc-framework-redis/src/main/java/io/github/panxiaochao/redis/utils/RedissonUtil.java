package io.github.panxiaochao.redis.utils;

import io.github.panxiaochao.core.utils.SpringContextUtil;
import io.github.panxiaochao.redis.utils.function.RLockTryFail;
import io.github.panxiaochao.redis.utils.function.RLockTrySuccess;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * {@code RedissonUtil}
 * <p> Redisson 工具类
 *
 * @author Lypxc
 * @since 2023-06-27
 */
public class RedissonUtil {
    /**
     * LOGGER RedissonUtil.class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonUtil.class);

    /**
     * constructor private
     */
    private RedissonUtil() {
    }

    /**
     * 初始化
     */
    private static final RedissonUtil REDISSON_UTIL = new RedissonUtil();

    /**
     * RedissonClient properties
     */
    private RedissonClient redissonClient;

    /**
     * 自己手动初始化 RedissonClient Bean instance
     *
     * @param redissonClient RedissonClient
     * @return RedissonUtil
     */
    public RedissonUtil initRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        return this;
    }

    /**
     * obtain static RedissonUtil instance
     *
     * @return RedissonUtil
     */
    public static RedissonUtil INSTANCE() {
        return REDISSON_UTIL;
    }

    /**
     * obtain RedissonClient
     *
     * @return RedissonClient
     */
    public RedissonClient ofRedissonClient() {
        final RedissonClient redissonClient =
                Optional.ofNullable(this.redissonClient)
                        .orElseGet(() -> SpringContextUtil.getInstance().getBean(RedissonClient.class));
        Objects.requireNonNull(redissonClient, "RedissonClient is null, check env is a web application or iniRedissonClient ！");
        return redissonClient;
    }

    /**
     * Returns id of this Redisson instance ID
     *
     * @return String
     */
    public String getRedissonId() {
        return ofRedissonClient().getId();
    }

    // ------------------------------- Object 类型操作 --------------------------------

    /**
     * set the value
     *
     * @param key   key
     * @param value T value
     */
    public <T> void set(String key, T value) {
        set(key, value, 0, null);
    }

    /**
     * obtain the v
     *
     * @param key key
     * @return value
     */
    public <T> T get(String key) {
        RBucket<T> rBucket = getRBucket(key);
        return rBucket.get();
    }

    /**
     * Set the value
     *
     * @param key        key
     * @param value      T value
     * @param timeToLive expire time
     * @param timeUnit   time unit
     */
    public <T> void set(String key, T value, long timeToLive, TimeUnit timeUnit) {
        if (timeToLive <= 0) {
            getRBucket(key).set(value);
        } else {
            getRBucket(key).set(value, timeToLive, timeUnit);
        }
    }

    /**
     * obtain the RBucket
     *
     * @param name name of object
     * @return RBucket
     */
    private <T> RBucket<T> getRBucket(String name) {
        return ofRedissonClient().getBucket(name);
    }

    // ------------------------------- 限流    类型操作 --------------------------------

    /**
     * 限流
     *
     * @param key          限流key
     * @param rateType     限流类型
     * @param rate         速率
     * @param rateInterval 速率间隔
     * @return -1 表示失败
     */
    public long setRateLimiter(String key, RateType rateType, long rate, long rateInterval) {
        RRateLimiter rateLimiter = getRRateLimiter(key);
        boolean trySetRateSuccess = rateLimiter.trySetRate(rateType, rate, rateInterval, RateIntervalUnit.SECONDS);
        // 第一次成功 拿锁后进行设置过期时间
        if (trySetRateSuccess) {
            // 设置过期时间，和速率一样，防止缓存残留
            rateLimiter.expire(Duration.ofSeconds(rateInterval));
        }
        if (rateLimiter.tryAcquire()) {
            return rateLimiter.availablePermits();
        } else {
            return -1L;
        }
    }

    /**
     * obtain the RRateLimiter
     *
     * @param name name of object
     * @return RRateLimiter
     */
    private RRateLimiter getRRateLimiter(String name) {
        return ofRedissonClient().getRateLimiter(name);
    }

    // ------------------------------- 二进制流 类型操作 --------------------------------

    /**
     * obtain the RBinaryStream
     *
     * @param name name of object
     * @return RBinaryStream
     */
    private RBinaryStream getRBinaryStream(String name) {
        return ofRedissonClient().getBinaryStream(name);
    }

    // ------------------------------- List   类型操作 --------------------------------

    /**
     * obtain the getRList
     *
     * @param name name of object
     * @return getRList
     */
    private <T> RList<T> getRList(String name) {
        return ofRedissonClient().getList(name);
    }

    // ------------------------------- Set    类型操作 --------------------------------

    /**
     * obtain the RSet
     *
     * @param name name of object
     * @return RSet
     */
    private <T> RSet<T> getRSet(String name) {
        return ofRedissonClient().getSet(name);
    }

    // ------------------------------- Map    类型操作 --------------------------------

    /**
     * obtain the RMap
     *
     * @param name name of object
     * @return RMap
     */
    private <K, V> RMap<K, V> getRMap(String name) {
        return ofRedissonClient().getMap(name);
    }

    // ------------------------------- 原子Long 类型操作 --------------------------------

    /**
     * obtain the RAtomicLong
     *
     * @param name name of object
     * @return RAtomicLong
     */
    private RAtomicLong getRAtomicLong(String name) {
        return ofRedissonClient().getAtomicLong(name);
    }

    // ------------------------------- 字节    类型操作 --------------------------------

    /**
     * obtain the RBitSet
     *
     * @param name name of object
     * @return RBitSet
     */
    private RBitSet getRBitSet(String name) {
        return ofRedissonClient().getBitSet(name);
    }

    // ------------------------------- 可重入锁 类型操作 --------------------------------

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

    /**
     * obtain RLock
     *
     * @param lockName the lock name
     * @return RLock object
     */
    public RLock rLock(String lockName) {
        return ofRedissonClient().getLock(lockName);
    }
}
