package io.github.panxiaochao.common.plugin.datasource.core.wrapper;

import io.github.panxiaochao.common.plugin.datasource.builder.DefaultDataSourceBuilder;
import io.github.panxiaochao.common.plugin.datasource.core.database.TableMeta;
import io.github.panxiaochao.common.plugin.datasource.po.ITable;
import io.github.panxiaochao.common.utils.date.LocalDateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * {@code DatabaseMetaDataWrapper}
 * <p> description: 数据库元数据包装类
 *
 * @author Lypxc
 * @since 2023-04-20
 */
public class DatabaseMetaDataWrapper {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // private final DatabaseMetaData databaseMetaData;
    //
    // private final String catalog;
    //
    // private final String schema;

    public DatabaseMetaDataWrapper(DefaultDataSourceBuilder dataSourceBuilder) {
        // try {
        //     Connection connection = dataSourceBuilder.getConn();
        //     this.databaseMetaData = connection.getMetaData();
        //     this.catalog = connection.getCatalog();
        //     this.schema = dataSourceBuilder.getDefaultSchema();
        // } catch (SQLException e) {
        //     throw new RuntimeException("获取元数据错误:", e);
        // }
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
}
