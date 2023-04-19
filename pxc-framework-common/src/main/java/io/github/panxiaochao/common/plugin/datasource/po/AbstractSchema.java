package io.github.panxiaochao.common.plugin.datasource.po;

import java.util.List;

/**
 * {@code AbstractSchema}
 * <p> description: 数据库 名接口 抽象类
 *
 * @author Lypxc
 * @since 2023-04-19
 */
public abstract class AbstractSchema implements ISchema {
    private String schema;

    private List<? extends ITable> tables;

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
     * 数据库 表数组
     *
     * @return the list of tables
     */
    @Override
    public List<? extends ITable> getTables() {
        return this.tables;
    }
}
