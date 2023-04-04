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
package io.github.panxiaochao.common.ienums;

/**
 * {@code IEnum}
 * <p> 自定义回复枚举类
 *
 * @author Mr_LyPxc
 * @since 2022-05-04
 */
public interface IResponseEnum<T> {

    /**
     * 状态码
     *
     * @return T类型
     */
    T getCode();


    /**
     * 状态码对应注释
     *
     * @return 返回信息
     */
    String getMessage();

    /**
     * 根据code获取message
     *
     * @param code 错误码
     * @return 返回信息
     */
    String ofCode(T code);
}
