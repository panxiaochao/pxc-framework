package io.github.panxiaochao.operate.log.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * {@code MethodCostContext}
 * <p> 方法时间耗时上下文，使用 alibaba ttl 工具
 *
 * @author Lypxc
 * @since 2023-07-03
 */
public final class MethodCostContext {

    /**
     * 存储毫秒
     */
    private static final TransmittableThreadLocal<Long> METHOD_COST_TIME_LOCAL = new TransmittableThreadLocal<>();

    public static void setMethodCostTime(long costTime) {
        METHOD_COST_TIME_LOCAL.set(costTime);
    }

    /**
     * 获取时间毫秒数
     */
    public static long getMethodCostTime() {
        return METHOD_COST_TIME_LOCAL.get();
    }

    public static void removeMethodCostTime() {
        METHOD_COST_TIME_LOCAL.remove();
    }
}
