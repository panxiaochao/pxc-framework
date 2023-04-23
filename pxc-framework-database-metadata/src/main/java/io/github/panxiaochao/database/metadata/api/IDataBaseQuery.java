package io.github.panxiaochao.database.metadata.api;

import io.github.panxiaochao.database.metadata.po.IColumn;
import io.github.panxiaochao.database.metadata.po.ITable;

import java.util.List;

/**
 * {@code IDataBaseQuery}
 * <p> description: 通用数据库 查询接口
 *
 * @author Lypxc
 * @since 2023-04-14
 */
public interface IDataBaseQuery {

    /**
     * 查询所有表信息
     *
     * @return the list of tables
     */
    List<ITable> queryTables();

    /**
     * 根据表名查询表信息
     *
     * @param queryTableName the query name of the table
     * @return the ITable
     */
    ITable queryTable(String queryTableName);

    /**
     * 根据表名获取列信息
     *
     * @param tableName the name of the table
     * @return the list of columns
     */
    List<IColumn> queryTableColumns(String tableName);
}