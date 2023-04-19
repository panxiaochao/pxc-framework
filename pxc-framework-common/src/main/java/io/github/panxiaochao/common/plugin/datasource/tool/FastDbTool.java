package io.github.panxiaochao.common.plugin.datasource.tool;

import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;

/**
 * {@code FastDbTool}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public class FastDbTool {

    /**
     * 数据源配置 Builder
     */
    private final DefaultDataSourceBuilder.Builder dataSourceBuilder;

    public FastDbTool(DefaultDataSourceBuilder.Builder dataSourceBuilder) {
        this.dataSourceBuilder = dataSourceBuilder;
    }

    public static FastDbTool create(String url, String username, String password) {
        return new FastDbTool(new DefaultDataSourceBuilder.Builder(url, username, password));
    }

    public static FastDbTool create(DefaultDataSourceBuilder.Builder dataSourceConfigBuilder) {
        return new FastDbTool(dataSourceConfigBuilder);
    }

    // private String getSchema() {
    //     return dataSourceBuilder.;
    // }
}
