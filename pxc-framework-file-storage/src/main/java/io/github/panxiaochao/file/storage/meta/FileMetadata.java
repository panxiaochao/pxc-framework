package io.github.panxiaochao.file.storage.meta;

/**
 * {@code FileInfoMeta}
 * <p> description: Core interface representing File Info.
 *
 * @author Lypxc
 * @see AbstractFileMetadata
 * @since 2023-03-14
 */
public interface FileMetadata {

    /**
     * 文件唯一标识 uuid
     *
     * @return the only id
     */
    String getUuid();

    /**
     * 文件大小，默认字节（byte）
     *
     * @return the file size
     */
    long getSize();

    /**
     * 原始文件名
     *
     * @return the original filename
     */
    String getOriginalFilename();

    /**
     * the file storage dest path
     *
     * @return the file storage dest path
     */
    String getStoragePath();

    /**
     * the file suffix
     *
     * @return the file suffix
     */
    String getFileSuffix();

    /**
     * the storage real filename
     *
     * @return the storage real filename
     */
    String getRealName();
}
