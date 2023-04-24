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
package io.github.panxiaochao.database.metadata.core.database;

import io.github.panxiaochao.database.metadata.po.IColumn;
import io.github.panxiaochao.database.metadata.po.ITable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code TableMeta}
 * <p> description: 数字库表 元数据
 *
 * @author Lypxc
 * @since 2023-04-20
 */
@Getter
@Setter
public class TableMeta implements ITable {

    private String schema;

    private String tableName;

    private String tableComment;

    private LocalDateTime createTime;

    private List<? extends IColumn> columns;
}
