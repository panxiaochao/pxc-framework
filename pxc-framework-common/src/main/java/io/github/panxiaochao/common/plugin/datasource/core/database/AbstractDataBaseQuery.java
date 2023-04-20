package io.github.panxiaochao.common.plugin.datasource.core.database;

import io.github.panxiaochao.common.plugin.datasource.api.IDataBaseQuery;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.DefaultQuerySqlDecorator;
import io.github.panxiaochao.common.plugin.datasource.core.wrapper.DatabaseMetaDataWrapper;
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

    protected final DefaultDataSourceBuilder dataSourceBuilder;

    protected final DatabaseMetaDataWrapper databaseMetaDataWrapper;

    protected AbstractDataBaseQuery(DefaultDataSourceBuilder dataSourceBuilder) {
        this.defaultQuerySqlDecorator = new DefaultQuerySqlDecorator(dataSourceBuilder);
        this.dataSourceBuilder = dataSourceBuilder;
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceBuilder);
    }
}
