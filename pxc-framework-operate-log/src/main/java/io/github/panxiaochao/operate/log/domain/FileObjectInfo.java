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
package io.github.panxiaochao.operate.log.domain;

import io.github.panxiaochao.common.filemeta.AbstractFileMetadata;
import org.springframework.web.multipart.MultipartFile;

/**
 * {@code FileObjectInfo}
 * <p> description: 文件对象信息
 *
 * @author Lypxc
 * @since 2023-03-17
 */
public final class FileObjectInfo extends AbstractFileMetadata {

    private static final long serialVersionUID = 2355445362725794577L;

    public FileObjectInfo(MultipartFile multipartFile) {
        super(null, multipartFile.getSize(), multipartFile.getOriginalFilename(), null, null, null);
    }
}
