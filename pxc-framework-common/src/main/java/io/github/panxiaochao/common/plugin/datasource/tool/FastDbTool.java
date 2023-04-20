package io.github.panxiaochao.common.plugin.datasource.tool;

import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.DefaultDataBaseQueryDecorator;
import io.github.panxiaochao.common.plugin.datasource.po.ITable;

import java.util.List;

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
    private final DefaultDataSourceBuilder dataSourceBuilder;

    private final DefaultDataBaseQueryDecorator dataBaseQueryDecorator;

    public FastDbTool(DefaultDataSourceBuilder dataSourceBuilder) {
        this.dataSourceBuilder = dataSourceBuilder;
        this.dataBaseQueryDecorator = new DefaultDataBaseQueryDecorator(dataSourceBuilder);
    }

    public static FastDbTool create(String url, String username, String password) {
        return new FastDbTool(new DefaultDataSourceBuilder.Builder(url, username, password).build());
    }

    public static FastDbTool create(DefaultDataSourceBuilder dataSourceConfigBuilder) {
        return new FastDbTool(dataSourceConfigBuilder);
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
}
