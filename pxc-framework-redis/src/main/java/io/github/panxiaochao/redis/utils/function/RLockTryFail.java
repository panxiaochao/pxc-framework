package io.github.panxiaochao.redis.utils.function;

/**
 * {@code RLockTryFail}
 * <p> 获取锁失败处理
 *
 * @author Lypxc
 * @since 2023-06-14
 */
@FunctionalInterface
public interface RLockTryFail {

    /**
     * 自定失败义处理方法
     */
    void errorHandle();
}
