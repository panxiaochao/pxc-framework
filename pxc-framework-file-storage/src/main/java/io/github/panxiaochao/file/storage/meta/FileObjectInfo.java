package io.github.panxiaochao.file.storage.meta;

/**
 * {@code FileObjectInfo}
 * <p> description: 文件对象信息
 *
 * @author Lypxc
 * @since 2023-03-17
 */
public final class FileObjectInfo extends AbstractFileMetadata {

    private static final long serialVersionUID = 2355445362725794577L;

    public FileObjectInfo(String uuid, long size, String originalFilename, String storagePath, String fileSuffix, String realName) {
        super(uuid, size, originalFilename, storagePath, fileSuffix, realName);
    }
}
