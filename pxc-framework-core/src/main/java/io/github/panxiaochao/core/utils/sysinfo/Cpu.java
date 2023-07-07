package io.github.panxiaochao.core.utils.sysinfo;

import io.github.panxiaochao.core.utils.ArithmeticUtil;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code Cpu}
 * <p>
 *
 * @author Lypxc
 * @since 2023-07-07
 */
@Setter
@ToString
public class Cpu {

    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public double getTotal() {
        return ArithmeticUtil.round(ArithmeticUtil.mul(total, 100), 2);
    }

    public double getSys() {
        return ArithmeticUtil.round(ArithmeticUtil.mul(sys / total, 100), 2);
    }

    public double getUsed() {
        return ArithmeticUtil.round(ArithmeticUtil.mul(used / total, 100), 2);
    }

    public double getWait() {
        return ArithmeticUtil.round(ArithmeticUtil.mul(wait / total, 100), 2);
    }

    public double getFree() {
        return ArithmeticUtil.round(ArithmeticUtil.mul(free / total, 100), 2);
    }
}
