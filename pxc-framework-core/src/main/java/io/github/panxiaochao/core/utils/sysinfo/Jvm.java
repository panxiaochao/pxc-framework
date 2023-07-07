package io.github.panxiaochao.core.utils.sysinfo;

import io.github.panxiaochao.core.utils.ArithmeticUtil;
import io.github.panxiaochao.core.utils.date.DateUtil;
import lombok.Setter;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * {@code Jvm}
 * <p>
 *
 * @author Lypxc
 * @since 2023-07-07
 */
@Setter
public class Jvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal() {
        return ArithmeticUtil.div(total, (1024 * 1024), 2);
    }

    public double getMax() {
        return ArithmeticUtil.div(max, (1024 * 1024), 2);
    }

    public double getFree() {
        return ArithmeticUtil.div(free, (1024 * 1024), 2);
    }

    public double getUsed() {
        return ArithmeticUtil.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return ArithmeticUtil.mul(ArithmeticUtil.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public String getHome() {
        return home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        return DateUtil.dateToString(new Date(startTime));
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        return (startTime - System.currentTimeMillis()) + "ms";
    }

    /**
     * 运行参数
     */
    public String getInputArgs() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
    }
}
