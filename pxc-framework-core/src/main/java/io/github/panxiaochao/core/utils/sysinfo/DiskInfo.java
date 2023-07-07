package io.github.panxiaochao.core.utils.sysinfo;

import lombok.Getter;
import lombok.Setter;

/**
 * {@code DiskInfo}
 * <p> DiskInfo Entity
 *
 * @author Lypxc
 * @since 2023-07-07
 */
@Getter
@Setter
public class DiskInfo {
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private double usage;
}
