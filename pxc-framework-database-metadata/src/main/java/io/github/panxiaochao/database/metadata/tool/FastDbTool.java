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
package io.github.panxiaochao.database.metadata.tool;

import io.github.panxiaochao.database.metadata.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.database.metadata.builder.DefaultRulesBuilder;
import io.github.panxiaochao.database.metadata.core.DefaultDataBaseQueryDecorator;
import io.github.panxiaochao.database.metadata.po.IColumn;
import io.github.panxiaochao.database.metadata.po.ITable;

import java.util.List;
import java.util.function.Consumer;

/**
 * {@code FastDbTool}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public class FastDbTool {

    /**
     * 数据源配置
     */
    private final DefaultDataSourceBuilder.Builder dataSourceBuilder;

    private final DefaultRulesBuilder.Builder rulesBuilder;

    private final DefaultDataBaseQueryDecorator dataBaseQueryDecorator;

    public FastDbTool(DefaultDataSourceBuilder.Builder dataSourceBuilder) {
        this.dataSourceBuilder = dataSourceBuilder;
        this.rulesBuilder = new DefaultRulesBuilder.Builder();
        this.dataBaseQueryDecorator =
                new DefaultDataBaseQueryDecorator(dataSourceBuilder.build(), this.rulesBuilder.build());
    }

    public static FastDbTool create(String url, String username, String password) {
        return new FastDbTool(new DefaultDataSourceBuilder.Builder(url, username, password));
    }

    public static FastDbTool create(DefaultDataSourceBuilder.Builder dataSourceConfigBuilder) {
        return new FastDbTool(dataSourceConfigBuilder);
    }

    /**
     * 策略配置
     *
     * @param consumer 自定义策略配置
     */
    public FastDbTool rulesBuilder(Consumer<DefaultRulesBuilder.Builder> consumer) {
        consumer.accept(this.rulesBuilder);
        return this;
    }

    /**
     * 查询所有表信息
     *
     * @return the list of tables
     */
    public List<ITable> queryTables() {
        return dataBaseQueryDecorator.queryTables();
    }

    /**
     * 查询单表信息
     *
     * @param tableName 查询的表名
     * @return the table
     */
    public ITable queryTables(String tableName) {
        return dataBaseQueryDecorator.queryTable(tableName);
    }

    /**
     * 根据表名 获取数据库字段
     *
     * @param tableName the name of the table
     * @return the columns
     */
    public List<IColumn> queryColumns(String tableName) {
        return dataBaseQueryDecorator.queryTableColumns(tableName);
    }
}
