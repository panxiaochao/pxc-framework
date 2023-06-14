package io.github.panxiaochao.redis.function;

/**
 * {@code RLockHandle}
 * <p> description: 获取锁失败处理
 *
 * @author Lypxc
 * @since 2023-06-14
 */
@FunctionalInterface
public interface RLockTryFail {

     /**
      * 自定义处理方法
      */
     void errorHandle();
}
