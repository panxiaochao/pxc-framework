package io.github.panxiaochao.common.plugin.datasource.po;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code AbstractTable}
 * <p> description: 数据库 表接口 抽象类
 *
 * @author Lypxc
 * @since 2023-04-19
 */
public abstract class AbstractTable implements ITable {

    private String schema;

    private String tableName;

    private String tableComment;

    private String tableCollation;

    private LocalDateTime createTime;

    private List<? extends IColumn> columns;

    /**
     * 数据库 名
     *
     * @return String
     */
    @Override
    public String getSchema() {
        return this.schema;
    }

    /**
     * 数据库 表名
     *
     * @return String
     */
    @Override
    public String getTableName() {
        return this.tableName;
    }

    /**
     * 表注释
     *
     * @return String
     */
    @Override
    public String getTableComment() {
        return this.tableName;
    }

    /**
     * 表的默认字符集和字符列排序规则
     *
     * @return String
     */
    @Override
    public String getTableCollation() {
        return this.tableCollation;
    }

    /**
     * 表创建时间
     *
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    /**
     * 数据库 字段数组
     *
     * @return the list of columns
     */
    @Override
    public List<? extends IColumn> getColumns() {
        return this.columns;
    }
}
