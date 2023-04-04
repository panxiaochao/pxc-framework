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
package io.github.panxiaochao.common.response;

import io.github.panxiaochao.common.enums.CommonResponseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * <p>Api返回响应体
 *
 * @param <T> 泛型参数
 * @author Lypxc
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "数据返回响应类", description = "数据返回响应类")
public class R<T> {

    /**
     * 响应码
     */
    @Schema(description = "响应码")
    private int code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 成功
     *
     * @param <T> 数据类型
     * @return 成功的响应
     */
    public static <T> R<T> ok() {
        return new R<>(CommonResponseEnum.OK.getCode(), CommonResponseEnum.OK.getMessage(), null);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 成功的响应
     */
    public static <T> R<T> ok(T data) {
        return new R<>(CommonResponseEnum.OK.getCode(), CommonResponseEnum.OK.getMessage(), data);
    }

    /**
     * @param dataSupplier 数据
     * @param <T>          数据类型
     * @return 成功的响应
     */
    public static <T> R<T> ok(Supplier<T> dataSupplier) {
        return new R<>(CommonResponseEnum.OK.getCode(), CommonResponseEnum.OK.getMessage(), dataSupplier.get());
    }

    /**
     * 成功
     *
     * @param message 自定义消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 成功的响应
     */
    public static <T> R<T> ok(String message, T data) {
        return new R<>(CommonResponseEnum.OK.getCode(), message, data);
    }

    /**
     * 失败
     *
     * @param <T> 数据类型
     * @return 失败的响应
     */
    public static <T> R<T> fail() {
        return fail(CommonResponseEnum.INTERNAL_SERVER_ERROR.getCode(), CommonResponseEnum.INTERNAL_SERVER_ERROR.getMessage(), null);
    }

    /**
     * 失败
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 失败的响应
     */
    public static <T> R<T> fail(String message, T data) {
        return fail(CommonResponseEnum.INTERNAL_SERVER_ERROR.getCode(), message, data);
    }

    /**
     * 失败
     *
     * @param code    响应码
     * @param message 响应消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 失败的响应
     */
    public static <T> R<T> fail(int code, String message, T data) {
        return new R<T>(code, message, data);
    }


    // ---- 增加链式编程 ----

    /**
     * update code
     *
     * @param code 码值
     * @return R
     */
    public R<T> code(int code) {
        this.code = code;
        return this;
    }

    /**
     * update message
     *
     * @param message 信息
     * @return R
     */
    public R<T> message(String message) {
        this.message = message;
        return this;
    }

    /**
     * update data
     *
     * @param data 数据
     * @return R
     */
    public R<T> date(T data) {
        this.data = data;
        return this;
    }
}
