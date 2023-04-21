package io.github.panxiaochao.common.plugin.datasource.core;

import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultRulesBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.database.AbstractDataBaseQuery;
import io.github.panxiaochao.common.plugin.datasource.core.database.ColumnMeta;
import io.github.panxiaochao.common.plugin.datasource.core.database.TableMeta;
import io.github.panxiaochao.common.plugin.datasource.po.IColumn;
import io.github.panxiaochao.common.plugin.datasource.po.ITable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<ITable> queryTables() {
        List<ITable> tables = new ArrayList<>();
        try {
            final String queryTableSql = querySqlDecorator.queryTablesSql();
            querySqlDecorator.executeQuery(queryTableSql, resultSetWrapper -> {
                String tableName = resultSetWrapper.getString(querySqlDecorator.getTableName());
                if (StringUtils.hasText(tableName)) {
                    String tableType = resultSetWrapper.getString(querySqlDecorator.getTableType());
                    // 判断表类型, 是否跳过视图
                    if (!(rulesBuilder.isSkipView() && "VIEW".equalsIgnoreCase(tableType))) {
                        ITable tableMeta = databaseMetaDataWrapper.buildTableMeta(
                                resultSetWrapper.getString(querySqlDecorator.getSchemaName()),
                                tableName,
                                resultSetWrapper.getComment(querySqlDecorator.getTableComment()),
                                resultSetWrapper.getString(querySqlDecorator.getTableCreateTime())
                        );
                        tables.add(tableMeta);
                    }
                }
            });
            // 加载套表字段
            tables.forEach(this::fillTableColumns);
        } catch (Exception e) {
            logger.error("queryTables is error", e);
        } finally {
            // 释放连接对象
            querySqlDecorator.closeConnection();
        }
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
        ITable tableMeta = null;
        if (!CollectionUtils.isEmpty(tables)) {
            tableMeta = tables.stream()
                    .filter(s -> s.getTableName().equalsIgnoreCase(queryTableName))
                    .findFirst()
                    .orElseGet(TableMeta::new);
        }
        return tableMeta;
    }

    /**
     * 填充数据库表中的字段信息
     *
     * @param iTable 数据库表
     */
    public void fillTableColumns(ITable iTable) {
        TableMeta tableMeta = (TableMeta) iTable;
        List<IColumn> columns = this.fillColumns(tableMeta.getTableName());
        tableMeta.setColumns(columns);
    }

    /**
     * 根据表名获取列信息, 全部获取列影响性能，在数据表很多的情况下
     *
     * @param tableName query Columns for TableName
     * @return the list of columns
     */
    @Override
    public List<IColumn> queryTableColumns(String tableName) {
        List<IColumn> columns = new ArrayList<>();
        try {
            columns = fillColumns(tableName);
        } catch (Exception e) {
            logger.error("queryTableColumns is error", e);
        } finally {
            querySqlDecorator.closeConnection();
        }
        return columns;
    }

    /**
     * 填充数据表字段
     *
     * @param tableName query Columns for TableName
     * @return the list of columns
     */
    private List<IColumn> fillColumns(String tableName) {
        List<IColumn> columns = new ArrayList<>();
        try {
            Map<String, ColumnMeta> columnsMap = databaseMetaDataWrapper.getColumnsMetaMap(tableName, false);
            String queryColumnSql = querySqlDecorator.queryColumnSql(tableName);
            querySqlDecorator.executeQuery(queryColumnSql, resultSetWrapper -> {
                String columnName = resultSetWrapper.getString(querySqlDecorator.getColumnName());
                ColumnMeta column = columnsMap.get(columnName);
                column.setSchema(resultSetWrapper.getString(querySqlDecorator.getSchemaName()));
                column.setTableName(resultSetWrapper.getString(querySqlDecorator.getTableName()));
                column.setOrdinalPosition(resultSetWrapper.getInt("ORDINAL_POSITION"));
                column.setColumnType(resultSetWrapper.getString(querySqlDecorator.getColumnType()));
                columns.add(column);
            });
        } catch (Exception e) {
            logger.error("queryTableColumns is error", e);
        }
        return columns;
    }

}
