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
package io.github.panxiaochao.database.metadata.core;

import io.github.panxiaochao.database.metadata.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.database.metadata.builder.DefaultRulesBuilder;
import io.github.panxiaochao.database.metadata.core.database.AbstractDataBaseQuery;
import io.github.panxiaochao.database.metadata.core.database.ColumnMeta;
import io.github.panxiaochao.database.metadata.core.database.TableMeta;
import io.github.panxiaochao.database.metadata.po.IColumn;
import io.github.panxiaochao.database.metadata.po.ITable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code DefaultDataBaseQueryDecorator}
 * <p> description: 默认数据库查询实现类
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public class DefaultDataBaseQueryDecorator extends AbstractDataBaseQuery {

    public DefaultDataBaseQueryDecorator(DefaultDataSourceBuilder dataSourceBuilder, DefaultRulesBuilder rulesBuilder) {
        super(dataSourceBuilder, rulesBuilder);
    }

    /**
     * 查询所有表信息
     *
     * @return the list of tables
     */
    @Override
    public List<ITable> queryTables() {
        return queryTables("");
    }

    /**
     * 根据表名查询数据库表
     *
     * @param tableName the query name of the table
     * @return the list of tables
     */
    private List<ITable> queryTables(String tableName) {
        List<ITable> tables = new ArrayList<>();
        try {
            final String queryTableSql =
                    StringUtils.hasText(tableName) ? querySqlDecorator.queryTablesSql(tableName) : querySqlDecorator.queryTablesSql();
            querySqlDecorator.executeQuery(queryTableSql, resultSetWrapper -> {
                String iTableName = resultSetWrapper.getString(querySqlDecorator.getTableName());
                String tableType = resultSetWrapper.getString(querySqlDecorator.getTableType());
                // 判断表类型, 是否跳过视图
                if (!(rulesBuilder.isSkipView() && "VIEW".equalsIgnoreCase(tableType))) {
                    ITable tableMeta = databaseMetaDataWrapper.buildTableMeta(
                            resultSetWrapper.getString(querySqlDecorator.getSchemaName()),
                            iTableName,
                            resultSetWrapper.getComment(querySqlDecorator.getTableComment()),
                            resultSetWrapper.getString(querySqlDecorator.getTableCreateTime())
                    );
                    tables.add(tableMeta);
                }
            });
            if (rulesBuilder.isFillColumns()) {
                // 加载套表字段
                tables.forEach(this::fillTableColumns);
            }
        } catch (Exception e) {
            logger.error("queryTables is error", e);
        } finally {
            // 释放连接对象
            querySqlDecorator.closeConnection();
        }
        return tables;
    }

    /**
     * 根据表名查询表信息
     *
     * @param tableName the query name of the table
     * @return the ITable
     */
    @Override
    public ITable queryTable(String tableName) {
        // 查询的结果 只有一条
        List<ITable> tables = this.queryTables(tableName);
        return CollectionUtils.isEmpty(tables) ? new TableMeta() : tables.get(0);
    }

    /**
     * 填充数据库表中的字段信息
     *
     * @param iTable 数据库表
     */
    public void fillTableColumns(ITable iTable) {
        TableMeta tableMeta = (TableMeta) iTable;
        List<IColumn> columns = this.fillColumns(tableMeta.getTableName());
        tableMeta.setColumns(columns);
    }

    /**
     * 根据表名获取列信息, 全部获取列影响性能，在数据表很多的情况下
     *
     * @param tableName query Columns for TableName
     * @return the list of columns
     */
    @Override
    public List<IColumn> queryTableColumns(String tableName) {
        List<IColumn> columns = new ArrayList<>();
        try {
            columns = fillColumns(tableName);
        } catch (Exception e) {
            logger.error("queryTableColumns is error", e);
        } finally {
            querySqlDecorator.closeConnection();
        }
        return columns;
    }

    /**
     * 填充数据表字段
     *
     * @param tableName query Columns for TableName
     * @return the list of columns
     */
    private List<IColumn> fillColumns(String tableName) {
        List<IColumn> columns = new ArrayList<>();
        try {
            Map<String, ColumnMeta> columnsMap = databaseMetaDataWrapper.getColumnsMetaMap(tableName, false);
            String queryColumnSql = querySqlDecorator.queryColumnSql(tableName);
            querySqlDecorator.executeQuery(queryColumnSql, resultSetWrapper -> {
                String columnName = resultSetWrapper.getString(querySqlDecorator.getColumnName());
                ColumnMeta column = columnsMap.get(columnName);
                column.setPrimaryKey(querySqlDecorator.isPrimaryKey(resultSetWrapper.getResultSet()));
                column.setSchema(resultSetWrapper.getString(querySqlDecorator.getSchemaName()));
                column.setTableName(resultSetWrapper.getString(querySqlDecorator.getTableName()));
                column.setOrdinalPosition(resultSetWrapper.getInt("ORDINAL_POSITION"));
                column.setColumnType(resultSetWrapper.getString(querySqlDecorator.getColumnType()));
                columns.add(column);
            });
        } catch (Exception e) {
            logger.error("fillColumns is error", e);
        }
        return columns;
    }

}
