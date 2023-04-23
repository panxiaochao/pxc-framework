package io.github.panxiaochao.database.metadata.core.wrapper;

import io.github.panxiaochao.common.utils.StringPoolUtil;
import io.github.panxiaochao.common.utils.date.LocalDateTimeUtil;
import io.github.panxiaochao.database.metadata.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.database.metadata.core.database.ColumnMeta;
import io.github.panxiaochao.database.metadata.core.database.TableMeta;
import io.github.panxiaochao.database.metadata.po.ITable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * {@code DatabaseMetaDataWrapper}
 * <p> description: 数据库元数据包装类
 *
 * @author Lypxc
 * @since 2023-04-20
 */
public class DatabaseMetaDataWrapper {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DatabaseMetaData databaseMetaData;

    private final String catalog;

    private final String schema;

    public DatabaseMetaDataWrapper(DefaultDataSourceBuilder dataSourceBuilder) {
        try {
            Connection connection = dataSourceBuilder.getConn();
            this.databaseMetaData = connection.getMetaData();
            this.catalog = connection.getCatalog();
            this.schema = connection.getSchema();
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据错误:", e);
        }
    }

    /**
     * 构建表元数据
     *
     * @param schema       数据库
     * @param tableName    表名
     * @param tableComment 表注释
     * @param createTime   创建时间
     * @return TableMeta
     */
    public ITable buildTableMeta(String schema, String tableName, String tableComment, String createTime) {
        TableMeta tableMeta = new TableMeta();
        tableMeta.setSchema(schema);
        tableMeta.setTableName(tableName);
        tableMeta.setTableComment(tableComment);
        if (StringUtils.hasText(createTime)) {
            tableMeta.setCreateTime(LocalDateTimeUtil.stringToLocalDateTime(createTime));
        }
        return tableMeta;
    }

    public Map<String, ColumnMeta> getColumnsMetaMap(String tableName, boolean queryPrimaryKey) {
        return getColumnsMetaMap(this.catalog, this.schema, tableName, queryPrimaryKey);
    }

    public Map<String, ColumnMeta> getColumnsMetaMap(String catalog, String schema, String tableName, boolean queryPrimaryKey) {
        Set<String> primaryKeys = new HashSet<>();
        if (queryPrimaryKey) {
            try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
                while (primaryKeysResultSet.next()) {
                    String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                    primaryKeys.add(columnName);
                }
                if (primaryKeys.size() > 1) {
                    logger.warn("当前表: {}，存在多主键", tableName);
                }
            } catch (SQLException e) {
                throw new RuntimeException("读取表主键信息:" + tableName + "错误:", e);
            }
        }
        Map<String, ColumnMeta> columnsInfoMap = new LinkedHashMap<>();
        try (ResultSet resultSet = databaseMetaData.getColumns(catalog, schema, tableName, "%")) {
            while (resultSet.next()) {
                ColumnMeta column = new ColumnMeta();
                String columnName = resultSet.getString("COLUMN_NAME");
                column.setColumnName(columnName);
                column.setPrimaryKey(primaryKeys.contains(columnName));
                column.setDataType(resultSet.getString("TYPE_NAME"));
                column.setColumnLength(resultSet.getInt("COLUMN_SIZE"));
                column.setScale(resultSet.getInt("DECIMAL_DIGITS"));
                column.setColumnComment(formatComment(resultSet.getString("REMARKS")));
                column.setColumnDefault(resultSet.getString("COLUMN_DEF"));
                column.setNullable(resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
                try {
                    column.setAutoIncrement("YES".equals(resultSet.getString("IS_AUTOINCREMENT")));
                } catch (SQLException sqlException) {
                    logger.warn("获取IS_AUTOINCREMENT出现异常:", sqlException);
                }
                columnsInfoMap.put(columnName, column);
            }
            return Collections.unmodifiableMap(columnsInfoMap);
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库表字段信息:" + tableName + "错误:", e);
        }
    }

    /**
     * @param comment 注释
     * @return 格式化内容
     */
    private String formatComment(String comment) {
        return StringUtils.hasText(comment) ? comment.replaceAll("\r\n", "\t") : StringPoolUtil.EMPTY;
    }

}
