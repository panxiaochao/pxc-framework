package io.github.panxiaochao.core.utils.sysinfo;

import io.github.panxiaochao.core.utils.ArithmeticUtil;
import io.github.panxiaochao.core.utils.date.DateUtil;
import java.lang.management.ManagementFactory;
import java.util.Date;
import lombok.Setter;

/**
 * {@code Jvm}
 * <p>
 * Jvm Entity
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
    // 格式化输出
    long nd = 1000 * 24 * 60 * 60;
    long nh = 1000 * 60 * 60;
    long nm = 1000 * 60;
    // long ns = 1000;
    // 获得两个时间的毫秒时间差异
    long diff = System.currentTimeMillis() - startTime;
    // 计算差多少天
    long day = diff / nd;
    // 计算差多少小时
    long hour = diff % nd / nh;
    // 计算差多少分钟
    long min = diff % nd % nh / nm;
    // 计算差多少秒//输出结果
    // long sec = diff % nd % nh % nm / ns;
    return day + "天" + hour + "小时" + min + "分钟";
  }

  /**
   * 运行参数
   */
  public String getInputArgs() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
  }

}
