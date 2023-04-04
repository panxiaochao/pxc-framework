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
package io.github.panxiaochao.common.enums;

import io.github.panxiaochao.common.exception.IExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code ArgumentResponseEnum}
 * <p> 参数校验异常枚举, code以7000开始
 *
 * @author Lypxc
 * @since 2022/11/27
 */
@Getter
@AllArgsConstructor
public enum ArgumentResponseEnum implements IExceptionAssert {
    /**
     * code message
     */
    VALID_ERROR(7000, "验证失败！");

    private final Integer code;

    private final String message;

    @Override
    public String ofCode(Integer code) {
        for (ArgumentResponseEnum value : ArgumentResponseEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getMessage();
            }
        }
        return null;
    }
}
