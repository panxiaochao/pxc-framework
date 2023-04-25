/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
