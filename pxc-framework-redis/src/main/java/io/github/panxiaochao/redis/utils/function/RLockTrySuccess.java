package io.github.panxiaochao.redis.utils.function;

/**
 * {@code RLockTrySuccess}
 * <p> d 获取锁成功处理
 *
 * @author Lypxc
 * @since 2023-06-14
 */
@FunctionalInterface
public interface RLockTrySuccess {

    /**
     * 自定义成功处理方法
     */
    void successHandle();
}