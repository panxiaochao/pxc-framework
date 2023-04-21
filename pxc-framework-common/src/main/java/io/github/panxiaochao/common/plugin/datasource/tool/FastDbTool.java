package io.github.panxiaochao.common.plugin.datasource.tool;

import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultRulesBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.DefaultDataBaseQueryDecorator;
import io.github.panxiaochao.common.plugin.datasource.po.ITable;

import java.util.List;
import java.util.function.Consumer;

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
    private final DefaultDataSourceBuilder.Builder dataSourceBuilder;

    private final DefaultRulesBuilder.Builder rulesBuilder;

    private final DefaultDataBaseQueryDecorator dataBaseQueryDecorator;

    public FastDbTool(DefaultDataSourceBuilder.Builder dataSourceBuilder) {
        this.dataSourceBuilder = dataSourceBuilder;
        this.rulesBuilder = new DefaultRulesBuilder.Builder();
        this.dataBaseQueryDecorator =
                new DefaultDataBaseQueryDecorator(dataSourceBuilder.build(), this.rulesBuilder.build());
    }

    public static FastDbTool create(String url, String username, String password) {
        return new FastDbTool(new DefaultDataSourceBuilder.Builder(url, username, password));
    }

    public static FastDbTool create(DefaultDataSourceBuilder.Builder dataSourceConfigBuilder) {
        return new FastDbTool(dataSourceConfigBuilder);
    }

    /**
     * 策略配置
     *
     * @param consumer 自定义策略配置
     */
    public FastDbTool rulesBuilder(Consumer<DefaultRulesBuilder.Builder> consumer) {
        consumer.accept(this.rulesBuilder);
        return this;
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
