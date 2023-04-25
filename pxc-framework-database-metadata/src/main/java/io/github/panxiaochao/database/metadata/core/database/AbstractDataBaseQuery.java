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

import io.github.panxiaochao.database.metadata.api.IDataBaseQuery;
import io.github.panxiaochao.database.metadata.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.database.metadata.builder.DefaultRulesBuilder;
import io.github.panxiaochao.database.metadata.core.DefaultQuerySqlDecorator;
import io.github.panxiaochao.database.metadata.core.wrapper.DatabaseMetaDataWrapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code AbstractDataBaseQuery}
 * <p> description: 数据库 查询抽象类
 *
 * @author Lypxc
 * @since 2023-04-14
 */
@Getter
public abstract class AbstractDataBaseQuery implements IDataBaseQuery {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final DefaultQuerySqlDecorator querySqlDecorator;

    protected final DefaultDataSourceBuilder dataSourceBuilder;

    protected final DefaultRulesBuilder rulesBuilder;

    protected final DatabaseMetaDataWrapper databaseMetaDataWrapper;

    protected AbstractDataBaseQuery(DefaultDataSourceBuilder dataSourceBuilder, DefaultRulesBuilder rulesBuilder) {
        this.querySqlDecorator = new DefaultQuerySqlDecorator(dataSourceBuilder);
        this.dataSourceBuilder = dataSourceBuilder;
        this.rulesBuilder = rulesBuilder;
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceBuilder);
    }
}
