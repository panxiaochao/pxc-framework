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
package io.github.panxiaochao.database.metadata.core.sql;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code MySqlQuery}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-18
 */
@Getter
public class MySqlQuerySql extends AbstractQuerySql {

    /**
     * 表信息查询 SQL
     */
    @Override
    public String queryTablesSql() {
        return "SELECT TABLE_SCHEMA, TABLE_NAME, CREATE_TIME, TABLE_COMMENT, TABLE_TYPE " +
                "FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA='%s'";
    }

    /**
     * 表信息查询 SQL
     *
     * @param tableName 数据库 表名
     */
    @Override
    public String queryTablesSql(String tableName) {
        return "SELECT TABLE_SCHEMA, TABLE_NAME, CREATE_TIME, TABLE_COMMENT, TABLE_TYPE " +
                "FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA='%s' AND TABLE_NAME='" + tableName + "'";
    }

    /**
     * 表字段信息查询 SQL
     */
    @Override
    public String queryColumnSql() {
        return "SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, COLLATION_NAME, COLUMN_TYPE, COLUMN_KEY, EXTRA, COLUMN_COMMENT " +
                "FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA='#schema' and TABLE_NAME='%s' " +
                "ORDER BY ORDINAL_POSITION";
    }

    @Override
    public String getSchemaName() {
        return "TABLE_SCHEMA";
    }

    @Override
    public String getTableName() {
        return "TABLE_NAME";
    }

    @Override
    public String getTableComment() {
        return "TABLE_COMMENT";
    }

    @Override
    public String getTableCreateTime() {
        return "CREATE_TIME";
    }

    @Override
    public String getTableType() {
        return "TABLE_TYPE";
    }

    @Override
    public String getColumnName() {
        return "COLUMN_NAME";
    }

    @Override
    public String getColumnType() {
        return "COLUMN_TYPE";
    }

    @Override
    public String getColumnComment() {
        return "COLUMN_COMMENT";
    }

    @Override
    public String getColumnKey() {
        return "COLUMN_KEY";
    }

    /**
     * 判断主键是否为identity
     *
     * @param resultSet resultSet
     * @return boolean
     */
    @Override
    public boolean isAutoIncrement(ResultSet resultSet) {
        try {
            return "auto_increment".equals(resultSet.getString("Extra"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 是否是主键
     *
     * @param resultSet resultSet
     * @return boolean
     */
    @Override
    public boolean isPrimaryKey(ResultSet resultSet) {
        try {
            return "PRI".equalsIgnoreCase(resultSet.getString(getColumnKey()));
        } catch (SQLException e) {
            throw new RuntimeException(String.format("%s primaryKey is error", getColumnKey()), e);
        }
    }
}
