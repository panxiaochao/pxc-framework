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
package io.github.panxiaochao.database.metadata.po;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code ITable}
 * <p> description: 数据库 表接口
 *
 * @author Lypxc
 * @since 2023-04-14
 */
@Schema(description = "数据库表")
public interface ITable {

    /**
     * 数据库 名
     *
     * @return String
     */
    @Schema(description = "数据库名")
    String getSchema();

    /**
     * 数据库 表名
     *
     * @return String
     */
    @Schema(description = "表名")
    String getTableName();

    /**
     * 表注释
     *
     * @return String
     */
    @Schema(description = "表注释")
    String getTableComment();

    /**
     * 表创建时间
     *
     * @return LocalDateTime
     */
    @Schema(description = "表创建时间")
    LocalDateTime getCreateTime();

    /**
     * 数据库 字段数组
     *
     * @return the list of columns
     */
    @Schema(description = "数据库字段数组")
    List<? extends IColumn> getColumns();
}
