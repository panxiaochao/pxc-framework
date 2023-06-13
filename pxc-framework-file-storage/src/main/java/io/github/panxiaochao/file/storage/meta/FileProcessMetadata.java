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
package io.github.panxiaochao.file.storage.meta;

import io.github.panxiaochao.common.filemeta.AbstractFileMetadata;
import io.github.panxiaochao.common.utils.StringPoolUtil;
import io.github.panxiaochao.file.storage.enums.FileStorageResponseEnum;
import io.github.panxiaochao.file.storage.excetion.FileStorageRuntimeException;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * {@code FileProcessMetadata}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-16
 */
@Getter
public final class FileProcessMetadata extends AbstractFileMetadata {

    private static final long serialVersionUID = -5882172349756970434L;

    private final MultipartFile multipartFile;

    private final FileStorageProperties fileStorageProperties;

    public FileProcessMetadata(MultipartFile multipartFile, FileStorageProperties fileStorageProperties, String uuid) {
        this(multipartFile, fileStorageProperties, uuid, multipartFile.getSize(), multipartFile.getOriginalFilename(), fileStorageProperties.getStoragePath(), null, null);
    }

    public FileProcessMetadata(MultipartFile multipartFile, FileStorageProperties fileStorageProperties, String uuid, long size, String originalFilename, String storagePath, String fileSuffix, String realName) {
        super(uuid, size, originalFilename, storagePath, fileSuffix, realName);
        this.multipartFile = multipartFile;
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * the storage real filename
     *
     * @return the storage real filename
     */
    @Override
    public String getRealName() {
        return System.currentTimeMillis() + StringPoolUtil.DOT + getSuffix(super.getOriginalFilename());
    }

    /**
     * Return the contents of the file as an array of bytes.
     *
     * @return the contents of the file as bytes, or an empty byte array if empty
     */
    public byte[] getBytes() {
        try {
            return this.multipartFile.getBytes();
        } catch (IOException e) {
            throw new FileStorageRuntimeException(FileStorageResponseEnum.FILE_STORAGE_UPLOAD_ERROR, e);
        }
    }

    /**
     * Return the content type of the file
     *
     * @return the content type, or {@code null} if not defined
     */
    public String getContentType() {
        return this.multipartFile.getContentType();
    }

    /**
     * save the received file to the given destination file.
     *
     * @param dest the destination file
     */
    public void saveTo(File dest) {
        if (!this.multipartFile.isEmpty()) {
            try {
                this.multipartFile.transferTo(dest);
            } catch (IOException e) {
                if (!dest.isDirectory() && dest.exists()) {
                    // 消除 Result of 'File.delete()' is ignored
                    boolean flag = dest.delete();
                }
                throw new FileStorageRuntimeException(FileStorageResponseEnum.FILE_STORAGE_SAVE_ERROR, e);
            }
        }
    }

    /**
     * 转化为文件对象信息
     *
     * @return the file object info
     */
    public FileObjectInfo toFileObjectInfo() {
        return new FileObjectInfo(
                this.getUuid(),
                this.getSize(),
                this.getOriginalFilename(),
                this.getStoragePath(),
                this.getFileSuffix(),
                this.getRealName()
        );
    }
}
