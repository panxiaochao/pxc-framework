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
package io.github.panxiaochao.file.storage.engine;

import io.github.panxiaochao.common.filemeta.FileMetadata;
import io.github.panxiaochao.common.utils.StringPoolUtil;
import io.github.panxiaochao.common.utils.UuidUtil;
import io.github.panxiaochao.file.storage.context.DefaultFileStorageContext;
import io.github.panxiaochao.file.storage.context.FileStorageContext;
import io.github.panxiaochao.file.storage.core.FileStorageType;
import io.github.panxiaochao.file.storage.meta.FileProcessMetadata;
import io.github.panxiaochao.file.storage.processor.FileStorageProcessor;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * {@code FileStorageEngine}
 * <p> description: 文件存储引擎
 *
 * @author Lypxc
 * @since 2023-03-14
 */
@Getter
@Setter
public class FileStorageEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageEngine.class);

    private final FileStorageProcessor<? extends FileMetadata> processor;

    private final FileStorageProperties fileStorageProperties;

    public FileStorageEngine(FileStorageProcessor<? extends FileMetadata> processor, FileStorageProperties fileStorageProperties) {
        Assert.notNull(processor, "processor cannot be null");
        Assert.notNull(fileStorageProperties.getStoragePath(), "file-storage.storagePath cannot be null");
        this.processor = processor;
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * 上传文件
     *
     * @param multipartFile A representation of an uploaded file received in a multipart request.
     * @return the upload file info
     */
    public FileMetadata upload(MultipartFile multipartFile) {
        Objects.requireNonNull(multipartFile, () -> "multipartFile is null!");
        FileProcessMetadata fileProcessMetadata = new FileProcessMetadata(
                multipartFile,
                fileStorageProperties,
                UuidUtil.getSimpleUUID()
        );
        // 限制后缀上传
        final String limitFileSuffix = fileStorageProperties.getLimitFileSuffix();
        if (StringUtils.hasText(limitFileSuffix)) {
            List<String> limitFileSuffixArr = Arrays.asList(limitFileSuffix.split(StringPoolUtil.COMMA));
            if (limitFileSuffixArr.contains(fileProcessMetadata.getFileSuffix())) {
                return null;
            }
        }
        // 设置上下文
        FileStorageType storageType = new FileStorageType(fileStorageProperties.getStorageType());
        DefaultFileStorageContext.Builder storageBuilder = DefaultFileStorageContext.builder()
                .storageType(storageType)
                .fileStorageProperties(fileStorageProperties)
                .fileMetadata(fileProcessMetadata);
        FileStorageContext fileStorageContext = storageBuilder.build();
        return processor.process(fileStorageContext);
    }

    // @Deprecated
    // public boolean delete(String path) {
    //     return true;
    // }
}
