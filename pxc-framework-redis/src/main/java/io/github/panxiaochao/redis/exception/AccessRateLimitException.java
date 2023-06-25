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
package io.github.panxiaochao.redis.exception;

import io.github.panxiaochao.common.exception.BaseRuntimeException;
import io.github.panxiaochao.common.ienums.IResponseEnum;

/**
 * {@code AccessRateLimitException}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-05-26
 */
public class AccessRateLimitException extends BaseRuntimeException {
    private static final long serialVersionUID = -2293325649017181202L;

    public AccessRateLimitException() {
        super();
    }

    public AccessRateLimitException(IResponseEnum<Integer> responseEnum) {
        super(responseEnum);
    }

    public AccessRateLimitException(IResponseEnum<Integer> responseEnum, Throwable cause) {
        super(responseEnum, cause);
    }

    public AccessRateLimitException(IResponseEnum<Integer> responseEnum, String message) {
        super(responseEnum, message);
    }

    public AccessRateLimitException(IResponseEnum<Integer> responseEnum, String message, Throwable cause) {
        super(responseEnum, message, cause);
    }

    public AccessRateLimitException(IResponseEnum<Integer> responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public AccessRateLimitException(IResponseEnum<Integer> responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
