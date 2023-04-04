package io.github.panxiaochao.file.storage.processor;

import io.github.panxiaochao.common.utils.StringPoolUtil;
import io.github.panxiaochao.file.storage.context.FileStorageContext;
import io.github.panxiaochao.file.storage.core.FileStorageType;
import io.github.panxiaochao.file.storage.core.sftp.client.SftpClient;
import io.github.panxiaochao.file.storage.meta.FileObjectInfo;
import io.github.panxiaochao.file.storage.meta.FileProcessMetadata;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

/**
 * {@code LocalStorageProcessor}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public class SftpStorageProcessor implements FileStorageProcessor<FileObjectInfo> {

    @Resource
    private SftpClient sftpClient;

    /**
     * SFTP 存储处理
     *
     * @param context return {@code FileStorageContext}
     * @return the FileObjectInfo
     */
    @Override
    public FileObjectInfo process(FileStorageContext context) {
        if (!FileStorageType.SFTP_STORAGE.equals(context.getStorageType())) {
            return null;
        }
        FileProcessMetadata fileProcessMetadata = context.getFileMetadata();
        final FileStorageProperties storageProperties = context.getFileStorageProperties();
        String tempFilePath = fileProcessMetadata.getStoragePath();
        if (StringUtils.hasText(storageProperties.getModule())) {
            tempFilePath += storageProperties.getModule() + StringPoolUtil.SLASH;
        }
        // 开始存储文件
        sftpClient.uploadFile(new ByteArrayInputStream(fileProcessMetadata.getBytes()), tempFilePath, fileProcessMetadata.getRealName());
        return fileProcessMetadata.toFileObjectInfo();
    }
}
