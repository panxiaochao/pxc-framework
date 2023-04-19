package io.github.panxiaochao.common.plugin.datasource.po;

import java.util.List;

/**
 * {@code ISchema}
 * <p> description: 数据库 名接口
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public interface ISchema {

    /**
     * 数据库 名
     *
     * @return String
     */
    String getSchema();

    /**
     * 数据库 表数组
     *
     * @return the list of tables
     */
    List<? extends ITable> getTables();
}
