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
package io.github.panxiaochao.file.storage.core;

import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * {@code FileStorageType}
 * <p> description: 文件存储类型
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public class FileStorageType implements Serializable {

    private static final long serialVersionUID = 5330567190131343864L;

    public static final FileStorageType LOCAL_STORAGE = new FileStorageType("local");
    public static final FileStorageType SFTP_STORAGE = new FileStorageType("sftp");

    private final String value;

    public FileStorageType(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        FileStorageType that = (FileStorageType) obj;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
