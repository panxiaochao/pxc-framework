package io.github.panxiaochao.file.storage.meta;

import io.github.panxiaochao.common.utils.StringPoolUtil;
import io.github.panxiaochao.common.utils.UuidUtil;
import io.github.panxiaochao.file.storage.utils.FileNameUtil;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * {@code AbstractFileMetadata}
 * <p> description: Base class for File Info implementations.
 *
 * @author Lypxc
 * @since 2023-03-16
 */
@Getter
public abstract class AbstractFileMetadata implements FileMetadata, Serializable {

    private static final long serialVersionUID = -6014865308038698860L;

    /**
     * 文件唯一标识 uuid
     */
    private final String uuid;

    /**
     * 原始文件大小
     */
    private final long size;

    /**
     * 原始文件名
     */
    private final String originalFilename;

    /**
     * 存储路径
     */
    private final String storagePath;

    /**
     * 文件后缀
     */
    private final String fileSuffix;

    /**
     * 真实存储文件名
     */
    private final String realName;

    protected AbstractFileMetadata(String uuid, long size, String originalFilename, String storagePath, String fileSuffix, String realName) {
        Assert.hasText(uuid, "uuid cannot be empty");
        Assert.hasText(originalFilename, "originalFilename cannot be empty");
        if (!StringUtils.hasText(fileSuffix)) {
            fileSuffix = FileNameUtil.getSuffix(originalFilename);
        }
        if (!StringUtils.hasText(realName)) {
            realName = UuidUtil.getSimpleUUID() + StringPoolUtil.DOT + fileSuffix;
        }
        this.uuid = uuid;
        this.size = size;
        this.originalFilename = originalFilename;
        this.storagePath = verifyPath(storagePath);
        this.fileSuffix = fileSuffix;
        this.realName = realName;
    }

    /**
     * <p> 验证存储路径，转换路径 '\\' or '/' 全部转换为正斜杠
     * <p> 兼容 Windows 和 Linux
     *
     * @param storagePath 存储路径
     * @return 存储路径
     */
    private String verifyPath(String storagePath) {
        storagePath = storagePath.replaceAll(StringPoolUtil.BACK_SLASH, StringPoolUtil.SLASH);
        if (!storagePath.endsWith(StringPoolUtil.SLASH)) {
            storagePath += StringPoolUtil.SLASH;
        }
        return storagePath;
    }
}
