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
