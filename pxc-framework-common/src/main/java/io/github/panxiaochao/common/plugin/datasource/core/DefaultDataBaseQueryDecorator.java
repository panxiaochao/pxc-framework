package io.github.panxiaochao.common.plugin.datasource.core;

import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.database.AbstractDataBaseQuery;
import io.github.panxiaochao.common.plugin.datasource.core.database.TableMeta;
import io.github.panxiaochao.common.plugin.datasource.po.IColumn;
import io.github.panxiaochao.common.plugin.datasource.po.ISchema;
import io.github.panxiaochao.common.plugin.datasource.po.ITable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultDataBaseQueryDecorator(DefaultDataSourceBuilder defaultDataSourceBuilder) {
        super(defaultDataSourceBuilder);
    }

    /**
     * 查询数据库名字
     *
     * @return the Schema
     */
    @Override
    public ISchema querySchema() {
        return null;
    }

    /**
     * 查询所有表信息
     *
     * @return the list of tables
     */
    @Override
    public List<ITable> queryTables() {
        List<ITable> tables = new ArrayList<>();
        final String queryTableSql = defaultQuerySqlDecorator.queryTablesSql();
        defaultQuerySqlDecorator.executeQuery(queryTableSql, resultSet -> {
            String tableName = resultSet.getString(defaultQuerySqlDecorator.getTableName());
            if (StringUtils.hasText(tableName)) {
                String tableComment = resultSet.getComment(defaultQuerySqlDecorator.getTableComment());
                // 跳过视图 VIEW
                if (!"VIEW".equals(tableComment)) {
                    ITable tableMeta = databaseMetaDataWrapper.buildTableMeta(
                            resultSet.getString(defaultQuerySqlDecorator.getSchemaName()),
                            tableName,
                            tableComment,
                            resultSet.getString(defaultQuerySqlDecorator.getTableCreateTime())
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
        List<ITable> tables = this.queryTables();
        ITable tableMeta = new TableMeta();
        if (!CollectionUtils.isEmpty(tables)) {
            tableMeta = tables.stream()
                    .filter(s -> s.getTableName().equalsIgnoreCase(queryTableName))
                    .findFirst()
                    .orElseGet(TableMeta::new);
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
