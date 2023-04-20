package io.github.panxiaochao.common.plugin.datasource.builder;

import io.github.panxiaochao.common.plugin.datasource.api.IQuerySql;
import io.github.panxiaochao.common.plugin.datasource.enums.DatabaseType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * {@code DefaultDataSourceBuilder}
 * <p> description: 数据源创建
 *
 * @author Lypxc
 * @since 2023-04-18
 */
public class DefaultDataSourceBuilder {

    private DefaultDataSourceBuilder() {
    }

    /**
     * 数据库 查询语句
     */
    private IQuerySql querySql;

    /**
     * schemaName
     */
    private String schemaName;

    /**
     * 驱动连接的URL
     */
    @Getter
    private String url;

    /**
     * 数据库连接用户名
     */
    @Getter
    private String username;

    /**
     * 数据库连接密码
     */
    @Getter
    private String password;

    /**
     * 数据源实例
     */
    private DataSource dataSource;

    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * 数据库连接属性
     *
     * @since 3.5.3
     */
    private final Map<String, String> connectionProperties = new HashMap<>();

    /**
     * 查询方式
     */
    // private final DefaultQueryDecorator defaultQueryDecorator;
    public IQuerySql getDefaultQuerySql() {
        if (null == querySql) {
            DatabaseType databaseType = this.getDatabaseType();
            // 返回为空就是不支持目前的目数据读取
            Objects.requireNonNull(databaseType, "DatabaseType is not found");
            querySql = databaseType.getDefaultQuery();
        }
        return querySql;
    }

    /**
     * 判断数据库类型
     *
     * @return 类型枚举值，如果没找到，则返回 null
     */
    public DatabaseType getDatabaseType() {
        return this.getDatabaseType(this.url.toLowerCase());
    }

    /**
     * 判断数据库类型
     *
     * @param url url 数据库链接
     * @return 类型枚举值，如果没找到，则返回 null
     */
    private DatabaseType getDatabaseType(String url) {
        Objects.requireNonNull(url, "url is not null");
        if (url.contains(":mysql:") || url.contains(":cobar:")) {
            return DatabaseType.MYSQL;
        }
        return null;
    }

    /**
     * 创建数据库连接对象
     * 这方法建议只调用一次，毕竟只是代码生成，用一个连接就行。
     *
     * @return Connection
     */
    public Connection getConn() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            } else {
                synchronized (this) {
                    if (dataSource != null) {
                        connection = dataSource.getConnection();
                    } else {
                        Properties properties = new Properties();
                        connectionProperties.forEach(properties::setProperty);
                        properties.put("user", username);
                        properties.put("password", password);
                        // 使用元数据查询方式时，有些数据库需要增加属性才能读取注释
                        this.processProperties(properties);
                        this.connection = DriverManager.getConnection(url, properties);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private void processProperties(Properties properties) {
        if (this.getDatabaseType() == DatabaseType.MYSQL) {
            properties.put("remarks", "true");
            properties.put("useInformationSchema", "true");
        }
    }

    /**
     * 获取数据库默认schema
     *
     * @return 默认schema
     */
    public String getDefaultSchema() {
        DatabaseType databaseType = this.getDatabaseType();
        try {
            if (databaseType == DatabaseType.MYSQL) {
                this.schemaName = this.connection.getCatalog();
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取Schema报错", e);
        }
        // if (databaseType.POSTGRE_SQL == dbType) {
        //     //pg 默认 schema=public
        //     schema = "public";
        // } else if (DbType.KINGBASE_ES == dbType) {
        //     //kingbase 默认 schema=PUBLIC
        //     schema = "PUBLIC";
        // } else if (DbType.DB2 == dbType) {
        //     //db2 默认 schema=current schema
        //     schema = "current schema";
        // } else if (DbType.ORACLE == dbType) {
        //     //oracle 默认 schema=username
        //     schema = this.username.toUpperCase();
        // }
        return schemaName;
    }


    /**
     * 数据库配置构建者
     */
    public static class Builder {

        private final DefaultDataSourceBuilder dataSourceBuilder;

        public Builder() {
            this.dataSourceBuilder = new DefaultDataSourceBuilder();
        }

        public Builder(String url, String username, String password) {
            this();
            if (StringUtils.isBlank(url)) {
                throw new RuntimeException("url is not null");
            }
            this.dataSourceBuilder.url = url;
            this.dataSourceBuilder.username = username;
            this.dataSourceBuilder.password = password;
        }

        /**
         * 构造初始化方法
         *
         * @param dataSource 外部数据源实例
         */
        public Builder(DataSource dataSource) {
            this();
            this.dataSourceBuilder.dataSource = dataSource;
            try {
                Connection conn = dataSource.getConnection();
                this.dataSourceBuilder.url = conn.getMetaData().getURL();
                try {
                    this.dataSourceBuilder.schemaName = conn.getSchema();
                } catch (Throwable exception) {
                    // ignore  如果使用低版本的驱动，这里由于是1.7新增的方法，所以会报错，如果驱动太低，需要自行指定了。
                    throw new RuntimeException("JDK Version is not supported");
                }
                this.dataSourceBuilder.connection = conn;
                this.dataSourceBuilder.username = conn.getMetaData().getUserName();
            } catch (SQLException ex) {
                throw new RuntimeException("DateSourceBuilder is error", ex);
            }
        }

        /**
         * 设置数据库schema
         *
         * @param schemaName 数据库schema
         * @return this
         */
        public Builder schema(String schemaName) {
            this.dataSourceBuilder.schemaName = schemaName;
            return this;
        }

        /**
         * 增加数据库连接属性
         *
         * @param key   属性名
         * @param value 属性值
         * @return this
         */
        public Builder addConnectionProperty(String key, String value) {
            this.dataSourceBuilder.connectionProperties.put(key, value);
            return this;
        }

        /**
         * 构建数据库配置
         *
         * @return 数据库配置
         */
        public DefaultDataSourceBuilder build() {
            return this.dataSourceBuilder;
        }
    }
}
