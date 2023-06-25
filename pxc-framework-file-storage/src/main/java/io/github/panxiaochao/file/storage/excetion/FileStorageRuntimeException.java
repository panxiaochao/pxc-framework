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
package io.github.panxiaochao.file.storage.excetion;

import io.github.panxiaochao.common.exception.BaseRuntimeException;
import io.github.panxiaochao.common.ienums.IResponseEnum;

/**
 * {@code FileStorageRuntimeException}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-13
 */
public class FileStorageRuntimeException extends BaseRuntimeException {

    private static final long serialVersionUID = 3535550296824509656L;

    public FileStorageRuntimeException() {
        super();
    }

    public FileStorageRuntimeException(IResponseEnum<Integer> responseEnum) {
        super(responseEnum);
    }

    public FileStorageRuntimeException(IResponseEnum<Integer> responseEnum, Throwable cause) {
        super(responseEnum, cause);
    }

    public FileStorageRuntimeException(IResponseEnum<Integer> responseEnum, String message) {
        super(responseEnum, message);
    }

    public FileStorageRuntimeException(IResponseEnum<Integer> responseEnum, String message, Throwable cause) {
        super(responseEnum, message, cause);
    }

    public FileStorageRuntimeException(IResponseEnum<Integer> responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public FileStorageRuntimeException(IResponseEnum<Integer> responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
