package io.github.panxiaochao.common.plugin.datasource.po;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code ITable}
 * <p> description: 数据库 表接口
 *
 * @author Lypxc
 * @since 2023-04-14
 */
public interface ITable {

    /**
     * 数据库 名
     *
     * @return String
     */
    String getSchema();

    /**
     * 数据库 表名
     *
     * @return String
     */
    String getTableName();

    /**
     * 表注释
     *
     * @return String
     */
    String getTableComment();

    /**
     * 表的默认字符集和字符列排序规则
     *
     * @return String
     */
    String getTableCollation();

    /**
     * 表创建时间
     *
     * @return LocalDateTime
     */
    LocalDateTime getCreateTime();

    /**
     * 数据库 字段数组
     *
     * @return the list of columns
     */
    List<? extends IColumn> getColumns();
}
