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
package io.github.panxiaochao.common.builder.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@code PageResponse}
 * <p> description: 分页对象响应实体
 *
 * @author Lypxc
 * @since 2023-01-03
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分页对象响应")
public class PageObjectResponse<T> {

    /**
     * 分页信息
     */
    @Schema(name = "pagination", description = "分页信息")
    private Pagination pagination;

    /**
     * 对象数据
     */
    @Schema(name = "data", description = "对象数据")
    private T data;
}
