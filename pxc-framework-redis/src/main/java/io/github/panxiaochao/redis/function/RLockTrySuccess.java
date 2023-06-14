package io.github.panxiaochao.redis.function;

/**
 * {@code RLockHandle}
 * <p> description: 获取锁成功处理
 *
 * @author Lypxc
 * @since 2023-06-14
 */
@FunctionalInterface
public interface RLockTrySuccess {

    /**
     * 自定义处理方法
     */
    void successHandle();
}
