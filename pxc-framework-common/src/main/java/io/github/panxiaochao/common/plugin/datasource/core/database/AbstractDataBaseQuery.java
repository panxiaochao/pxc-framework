package io.github.panxiaochao.common.plugin.datasource.core.database;

import io.github.panxiaochao.common.plugin.datasource.api.IDataBaseQuery;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.DefaultQuerySqlDecorator;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code AbstractDataBaseQuery}
 * <p> description: 数据库 查询抽象类
 *
 * @author Lypxc
 * @since 2023-04-14
 */
@Getter
public abstract class AbstractDataBaseQuery implements IDataBaseQuery {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final DefaultQuerySqlDecorator defaultQuerySqlDecorator;

    protected final DefaultDataSourceBuilder defaultDataSourceBuilder;

    protected AbstractDataBaseQuery(DefaultDataSourceBuilder defaultDataSourceBuilder) {
        this.defaultQuerySqlDecorator = new DefaultQuerySqlDecorator(defaultDataSourceBuilder);
        this.defaultDataSourceBuilder = defaultDataSourceBuilder;
    }
}
