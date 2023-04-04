package io.github.panxiaochao.file.storage.processor;

import io.github.panxiaochao.common.utils.StringPoolUtil;
import io.github.panxiaochao.file.storage.context.FileStorageContext;
import io.github.panxiaochao.file.storage.core.FileStorageType;
import io.github.panxiaochao.file.storage.meta.FileObjectInfo;
import io.github.panxiaochao.file.storage.meta.FileProcessMetadata;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * {@code LocalStorageProcessor}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public class LocalStorageProcessor implements FileStorageProcessor<FileObjectInfo> {

    /**
     * 本地存储处理
     *
     * @param context return {@code FileStorageContext}
     * @return the FileObjectInfo
     */
    @Override
    public FileObjectInfo process(FileStorageContext context) {
        if (!FileStorageType.LOCAL_STORAGE.equals(context.getStorageType())) {
            return null;
        }
        FileProcessMetadata fileProcessMetadata = context.getFileMetadata();
        final FileStorageProperties storageProperties = context.getFileStorageProperties();
        String tempFilePath = fileProcessMetadata.getStoragePath();
        if (StringUtils.hasText(storageProperties.getModule())) {
            tempFilePath += storageProperties.getModule() + StringPoolUtil.SLASH;
        }
        tempFilePath += fileProcessMetadata.getRealName();
        // 开始存储文件
        fileProcessMetadata.saveTo(new File(tempFilePath));
        return fileProcessMetadata.toFileObjectInfo();
    }
}
