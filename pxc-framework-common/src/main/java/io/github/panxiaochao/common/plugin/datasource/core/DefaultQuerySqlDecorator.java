package io.github.panxiaochao.common.plugin.datasource.core;

import io.github.panxiaochao.common.plugin.datasource.api.IQuerySql;
import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.sql.AbstractQuerySql;
import io.github.panxiaochao.common.plugin.datasource.core.wrapper.ResultSetWrapper;
import io.github.panxiaochao.common.plugin.datasource.enums.DatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * {@code DefaultQueryDecorator}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public class DefaultQuerySqlDecorator extends AbstractQuerySql {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IQuerySql querySql;

    private final Connection connection;

    private final DatabaseType databaseType;

    private final String schema;

    public DefaultQuerySqlDecorator(DefaultDataSourceBuilder defaultDataSourceBuilder) {
        this.querySql = defaultDataSourceBuilder.getDefaultQuerySql();
        this.connection = defaultDataSourceBuilder.getConn();
        this.databaseType = defaultDataSourceBuilder.getDatabaseType();
        this.schema = defaultDataSourceBuilder.getDefaultSchema();
    }

    /**
     * 表信息查询 SQL
     */
    @Override
    public String queryTablesSql() {
        String tablesSql = querySql.queryTablesSql();
        return String.format(tablesSql, this.schema);
    }

    /**
     * 扩展查询字段信息，通过表名
     *
     * @param tableName 数据库表名
     * @return String
     */
    public String queryColumnSql(String tableName) {
        String columnsSql = querySql.queryColumnSql();
        return String.format(columnsSql, this.schema, tableName);
    }

    /**
     * 表字段信息查询 SQL
     */
    @Override
    public String queryColumnSql() {
        return querySql.queryColumnSql();
    }

    /**
     * 数据库名称
     */
    @Override
    public String getSchemaName() {
        return querySql.getSchemaName();
    }

    /**
     * 表名称
     */
    @Override
    public String getTableName() {
        return querySql.getTableName();
    }

    /**
     * 表注释
     */
    @Override
    public String getTableComment() {
        return querySql.getTableComment();
    }

    /**
     * 表创建时间
     */
    @Override
    public String getTableCreateTime() {
        return querySql.getTableCreateTime();
    }

    /**
     * 表类型
     */
    @Override
    public String getTableType() {
        return querySql.getTableType();
    }

    /**
     * 字段名称
     */
    @Override
    public String getColumnName() {
        return querySql.getColumnName();
    }

    /**
     * 字段类型
     */
    @Override
    public String getColumnType() {
        return querySql.getColumnType();
    }

    /**
     * 字段注释
     */
    @Override
    public String getColumnComment() {
        return querySql.getColumnComment();
    }

    /**
     * 主键字段
     */
    @Override
    public String getColumnKey() {
        return querySql.getColumnKey();
    }

    /**
     * 执行 SQL 查询，回调返回结果
     *
     * @param sql      执行SQL
     * @param consumer 结果处理
     */
    public void executeQuery(String sql, Consumer<ResultSetWrapper> consumer) {
        logger.debug("执行SQL: {}", sql);
        int count = 0;
        long start = System.nanoTime();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                consumer.accept(new ResultSetWrapper(resultSet, this.databaseType));
                count++;
            }
            long end = System.nanoTime();
            logger.debug("返回记录数:{}, 耗时(ms):{}", count, (end - start) / 1000000);
        } catch (SQLException e) {
            logger.error("execute is error", e);
        }
    }
}
