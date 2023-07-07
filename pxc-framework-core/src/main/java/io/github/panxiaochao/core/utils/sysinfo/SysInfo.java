package io.github.panxiaochao.core.utils.sysinfo;

import lombok.Getter;
import lombok.Setter;

/**
 * {@code ServerInfo}
 * <p>
 *
 * @author Lypxc
 * @since 2023-07-07
 */
@Setter
@Getter
public class SysInfo {
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
