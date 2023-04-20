package io.github.panxiaochao.common.plugin.datasource.core.database;

import io.github.panxiaochao.common.plugin.datasource.api.IDataBaseQuery;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultRulesBuilder;
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

    protected final DefaultQuerySqlDecorator querySqlDecorator;

    protected final DefaultDataSourceBuilder dataSourceBuilder;

    protected final DefaultRulesBuilder rulesBuilder;

    protected final DatabaseMetaDataWrapper databaseMetaDataWrapper;

    protected AbstractDataBaseQuery(DefaultDataSourceBuilder dataSourceBuilder, DefaultRulesBuilder rulesBuilder) {
        this.querySqlDecorator = new DefaultQuerySqlDecorator(dataSourceBuilder);
        this.dataSourceBuilder = dataSourceBuilder;
        this.rulesBuilder = rulesBuilder;
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceBuilder);
    }
}
