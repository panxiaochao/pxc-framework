package io.github.panxiaochao.file.storage.properties.nest;

import io.github.panxiaochao.file.storage.enums.PlatformEnum;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * {@code StorageLoca}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-15
 */
@Getter
@Setter
public class StorageSftp {
    /**
     * 主机
     */
    private String host;
    /**
     * 端口，默认22
     */
    private int port = 22;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * （可选）私钥路径
     */
    private String privateKeyPath;
    /**
     * （可选） 私钥口令
     */
    private String passphrase;
    /**
     * 编码，默认UTF-8
     */
    private Charset charset = StandardCharsets.UTF_8;
    /**
     * （可选）连接超时时长，单位毫秒，默认10秒
     */
    private int connectTimeout = 10 * 1000;
    /**
     * 存储平台, 默认Linux, 不允许修改
     */
    private final PlatformEnum platform = PlatformEnum.LINUX;
    /**
     * （可选）连接池配置
     */
    private Config poolConfig;

    @Getter
    @Setter
    public static class Config {
        /**
         * 最大对象数量，包含借出去的和空闲的
         */
        private int maxTotal = 8;
        /**
         * 最大空闲实例数，空闲超过此值将会被销毁淘汰
         */
        private int maxIdle = 8;
        /**
         * 最小空闲实例数，对象池将至少保留2个空闲对象
         */
        private int minIdle = 0;
        /**
         * 对象池满了，是否阻塞获取（false则借不到直接抛异常）
         */
        private boolean blockWhenExhausted = true;
        /**
         * BlockWhenExhausted为true时生效，对象池满了阻塞获取超时，不设置则阻塞获取不超时，也可在borrowObject方法传递第二个参数指定本次的超时时间
         */
        private long maxWaitMillis = -1;
        /**
         * 创建对象后是否验证对象，调用objectFactory#validateObject
         */
        private boolean testOnCreate = false;
        /**
         * 借用对象后是否验证对象, 配置true会降低性能
         */
        private boolean testOnBorrow = false;
        /**
         * 归还对象后是否验证对象 validateObject
         */
        private boolean testOnReturn = false;
        /**
         * 定时检查期间是否验证对象 validateObject
         */
        private boolean testWhileIdle = false;
        /**
         * 定时检查淘汰多余的对象, 启用单独的线程处理
         */
        private long timeBetweenEvictionRunsMillis = -1;
        /**
         * jmx监控，和springboot自带的jmx冲突，可以选择关闭此配置或关闭springboot的jmx配置
         */
        private boolean jmxEnabled = false;
    }
}
