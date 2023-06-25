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

import io.github.panxiaochao.common.filemeta.FileMetadata;
import io.github.panxiaochao.file.storage.context.FileStorageContext;
import org.springframework.lang.Nullable;

/**
 * {@code FileStorageProcessor}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public interface FileStorageProcessor<T extends FileMetadata> {

    /**
     * FileStorageContext
     *
     * @param storageContext return {@code FileStorageContext}
     * @return true or false
     */
    @Nullable
    T process(FileStorageContext storageContext);
}
