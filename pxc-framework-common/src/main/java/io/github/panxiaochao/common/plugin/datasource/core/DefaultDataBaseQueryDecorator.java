package io.github.panxiaochao.common.plugin.datasource.core;

import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultRulesBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.database.AbstractDataBaseQuery;
import io.github.panxiaochao.common.plugin.datasource.po.IColumn;
import io.github.panxiaochao.common.plugin.datasource.po.ITable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DefaultDataBaseQueryDecorator}
 * <p> description: 默认数据库查询实现类
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public class DefaultDataBaseQueryDecorator extends AbstractDataBaseQuery {
    // private final Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultDataBaseQueryDecorator(DefaultDataSourceBuilder dataSourceBuilder, DefaultRulesBuilder rulesBuilder) {
        super(dataSourceBuilder, rulesBuilder);
    }

    /**
     * 查询所有表信息
     *
     * @return the list of tables
     */
    @Override
    public List<? extends ITable> queryTables() {
        List<ITable> tables = new ArrayList<>();
        final String queryTableSql = querySqlDecorator.queryTablesSql();
        querySqlDecorator.executeQuery(queryTableSql, resultSet -> {
            String tableName = resultSet.getString(querySqlDecorator.getTableName());
            if (StringUtils.hasText(tableName)) {
                String tableType = resultSet.getString(querySqlDecorator.getTableType());
                // 判断表类型, 是否跳过视图
                if (!(rulesBuilder.isSkipView() && "VIEW".equalsIgnoreCase(tableType))) {
                    ITable tableMeta = databaseMetaDataWrapper.buildTableMeta(
                            resultSet.getString(querySqlDecorator.getSchemaName()),
                            tableName,
                            resultSet.getComment(querySqlDecorator.getTableComment()),
                            resultSet.getString(querySqlDecorator.getTableCreateTime())
                    );
                    tables.add(tableMeta);
                }
            }
        });
        return tables;
    }

    /**
     * 根据表名查询表信息
     *
     * @param queryTableName the query name of the table
     * @return the ITable
     */
    @Override
    public ITable queryTable(String queryTableName) {
        List<? extends ITable> tables = this.queryTables();
        ITable tableMeta = null;
        if (!CollectionUtils.isEmpty(tables)) {
            tableMeta = tables.stream()
                    .filter(s -> s.getTableName().equalsIgnoreCase(queryTableName))
                    .findFirst()
                    .orElse(null);
        }
        return tableMeta;
    }

    /**
     * 获取所有列信息
     *
     * @return the List of columns
     */
    @Override
    public List<? extends IColumn> queryTableColumns() {
        return null;
    }

    /**
     * 根据表名获取列信息
     *
     * @param tableName the name of the table
     * @return the list of columns
     */
    @Override
    public List<? extends IColumn> queryTableColumns(String tableName) {
        return null;
    }
}
