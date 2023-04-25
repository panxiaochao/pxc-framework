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

import io.github.panxiaochao.database.metadata.api.IQuerySql;
import io.github.panxiaochao.database.metadata.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.database.metadata.core.sql.AbstractQuerySql;
import io.github.panxiaochao.database.metadata.core.wrapper.ResultSetWrapper;
import io.github.panxiaochao.database.metadata.enums.DatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * {@code DefaultQueryDecorator}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public class DefaultQuerySqlDecorator extends AbstractQuerySql {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IQuerySql querySql;

    private final Connection connection;

    private final DatabaseType databaseType;

    private final String schema;

    public DefaultQuerySqlDecorator(DefaultDataSourceBuilder defaultDataSourceBuilder) {
        this.querySql = defaultDataSourceBuilder.getDefaultQuerySql();
        this.connection = defaultDataSourceBuilder.getConn();
        this.databaseType = defaultDataSourceBuilder.getDatabaseType();
        this.schema = defaultDataSourceBuilder.getDefaultSchema();
    }

    /**
     * 表信息查询 SQL
     */
    @Override
    public String queryTablesSql() {
        String tablesSql = querySql.queryTablesSql();
        return String.format(tablesSql, this.schema);
    }

    /**
     * 表信息查询 SQL
     *
     * @param tableName 数据库表名
     */
    @Override
    public String queryTablesSql(String tableName) {
        String tablesSql = querySql.queryTablesSql(tableName);
        return String.format(tablesSql, this.schema);
    }

    /**
     * 扩展查询字段信息，通过表名
     *
     * @param tableName 数据库表名
     * @return String
     */
    public String queryColumnSql(String tableName) {
        String columnsSql = querySql.queryColumnSql();
        return String.format(columnsSql.replace("#schema", this.schema), tableName);
    }

    /**
     * 表字段信息查询 SQL
     */
    @Override
    public String queryColumnSql() {
        return querySql.queryColumnSql();
    }

    /**
     * 数据库名称
     */
    @Override
    public String getSchemaName() {
        return querySql.getSchemaName();
    }

    /**
     * 表名称
     */
    @Override
    public String getTableName() {
        return querySql.getTableName();
    }

    /**
     * 表注释
     */
    @Override
    public String getTableComment() {
        return querySql.getTableComment();
    }

    /**
     * 表创建时间
     */
    @Override
    public String getTableCreateTime() {
        return querySql.getTableCreateTime();
    }

    /**
     * 表类型
     */
    @Override
    public String getTableType() {
        return querySql.getTableType();
    }

    /**
     * 字段名称
     */
    @Override
    public String getColumnName() {
        return querySql.getColumnName();
    }

    /**
     * 字段类型
     */
    @Override
    public String getColumnType() {
        return querySql.getColumnType();
    }

    /**
     * 字段注释
     */
    @Override
    public String getColumnComment() {
        return querySql.getColumnComment();
    }

    /**
     * 主键字段
     */
    @Override
    public String getColumnKey() {
        return querySql.getColumnKey();
    }

    /**
     * 是否自增
     */
    @Override
    public boolean isAutoIncrement(ResultSet resultSet) {
        return querySql.isAutoIncrement(resultSet);
    }

    /**
     * 是否主键
     */
    @Override
    public boolean isPrimaryKey(ResultSet resultSet) {
        return querySql.isPrimaryKey(resultSet);
    }

    /**
     * 执行 SQL 查询，回调返回结果
     *
     * @param sql      执行SQL
     * @param consumer 结果处理
     */
    public void executeQuery(String sql, Consumer<ResultSetWrapper> consumer) {
        logger.debug("执行SQL: {}", sql);
        int count = 0;
        long start = System.nanoTime();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                consumer.accept(new ResultSetWrapper(resultSet, this.databaseType));
                count++;
            }
            long end = System.nanoTime();
            logger.debug("返回记录数:{}, 耗时(ms):{}", count, (end - start) / 1000000);
        } catch (SQLException e) {
            logger.error("execute is error", e);
        }
    }

    public void closeConnection() {
        Optional.ofNullable(connection).ifPresent((con) -> {
            try {
                con.close();
            } catch (SQLException sqlException) {
                logger.error("close is error", sqlException);
            }
        });
    }
}
