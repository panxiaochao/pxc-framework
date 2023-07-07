package io.github.panxiaochao.core.utils.sysinfo;

import io.github.panxiaochao.core.utils.ArithmeticUtil;
import lombok.Setter;

/**
 * {@code Mem}
 * <p>
 *
 * @author Lypxc
 * @since 2023-07-07
 */
@Setter
public class Mem {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return ArithmeticUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return ArithmeticUtil.div(used, (1024 * 1024 * 1024), 2);
    }

    public double getFree() {
        return ArithmeticUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public double getUsage() {
        return ArithmeticUtil.mul(ArithmeticUtil.div(used, total, 4), 100);
    }
}
