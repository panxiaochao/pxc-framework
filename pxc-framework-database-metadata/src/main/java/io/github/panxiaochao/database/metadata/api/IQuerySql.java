package io.github.panxiaochao.database.metadata.api;

import java.sql.ResultSet;

/**
 * {@code IQuerySql}
 * <p> description: 通用数据库 查询语句接口
 *
 * @author Lypxc
 * @since 2023-04-14
 */
public interface IQuerySql {

    /**
     * 表信息查询 SQL
     */
    String queryTablesSql();

    /**
     * 表信息查询 SQL
     */
    String queryTablesSql(String tableName);

    /**
     * 表字段信息查询 SQL
     */
    String queryColumnSql();

    /**
     * 数据库名称
     */
    String getSchemaName();

    /**
     * 表名称
     */
    String getTableName();

    /**
     * 表注释
     */
    String getTableComment();

    /**
     * 表创建时间
     */
    String getTableCreateTime();

    /**
     * 表类型
     */
    String getTableType();

    /**
     * 字段名称
     */
    String getColumnName();

    /**
     * 字段类型
     */
    String getColumnType();

    /**
     * 字段注释
     */
    String getColumnComment();

    /**
     * 主键字段
     */
    String getColumnKey();

    /**
     * 判断主键是否为identity
     *
     * @param resultSet resultSet
     * @return 主键是否为identity
     */
    boolean isAutoIncrement(ResultSet resultSet);

    /**
     * 是否是主键
     *
     * @param resultSet resultSet
     * @return boolean
     */
    boolean isPrimaryKey(ResultSet resultSet);
}
