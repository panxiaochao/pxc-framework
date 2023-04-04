/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.db.document.generator;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * {@code DbDocumentGenerator}
 * <p> description: 数据库文档生成器 代码示例：
 *
 * <pre>
 *     public static void main(String[] args) {
 *         PxcDbDocumentGenerator.INSTANCE()
 *                 .driverClassName("com.mysql.cj.jdbc.Driver")
 *                 .jdbcUrl("jdbc:mysql://127.0.0.1:3306/xxx")
 *                 .username("root")
 *                 .password("123456")
 *                 .fileType(EngineFileType.HTML)
 *                 .fileOutputDir("/Users/XXX/Desktop")
 *                 .generateSql(false)
 *                 .build();
 *     }
 * </pre>
 *
 * @author Lypxc
 * @since 2022-12-27
 */
public final class PxcDbDocumentGenerator {

    private static final DbDocumentBuilder DOCUMENT_BUILDER = new DbDocumentBuilder();

    private PxcDbDocumentGenerator() {
    }

    public static DbDocumentBuilder INSTANCE() {
        return DOCUMENT_BUILDER;
    }

    public static class DbDocumentBuilder {
        /**
         * 驱动
         */
        private String driverClassName;
        /**
         * 数据库名
         */
        private String datasourceName;
        /**
         * 数据库链接
         */
        private String jdbcUrl;
        /**
         * 数据库用户名
         */
        private String username;
        /**
         * 数据库密码
         */
        private String password;
        /**
         * 输出路径
         */
        private String fileOutputDir;
        /**
         * 输出格式：Html Word MarkDown, 默认Html
         */
        private EngineFileType fileType = EngineFileType.HTML;
        /**
         * 引擎
         */
        private EngineTemplateType produceType = EngineTemplateType.freemarker;
        /**
         * 自定义模版引擎路径
         */
        private String customTemplate;
        /**
         * 文件名
         */
        private String fileName = "数据库设计文档";
        //
        /**
         * 忽略表
         */
        private List<String> ignoreTableName;
        /**
         * 忽略表前缀
         */
        private List<String> ignoreTablePrefix;
        /**
         * 忽略表后缀
         */
        private List<String> ignoreTableSuffix;
        /**
         * 指定表
         */
        private List<String> designatedTableName;
        /**
         * 指定表前缀
         */
        private List<String> designatedTablePrefix;
        /**
         * 指定表后缀
         */
        private List<String> designatedTableSuffix;
        //
        private String organization;
        private String organizationUrl;
        private String title = "数据库设计文档";
        private String version = "V1.0.0";
        private String description = "数据库设计文档生成";
        /**
         * 是否生成Sql脚本
         */
        private boolean generateSql = false;

        public DbDocumentBuilder driverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
            return this;
        }

        public DbDocumentBuilder datasourceName(String datasourceName) {
            this.datasourceName = datasourceName;
            return this;
        }

        public DbDocumentBuilder jdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
            return this;
        }

        public DbDocumentBuilder username(String username) {
            this.username = username;
            return this;
        }

        public DbDocumentBuilder password(String password) {
            this.password = password;
            return this;
        }

        public DbDocumentBuilder fileOutputDir(String fileOutputDir) {
            this.fileOutputDir = fileOutputDir;
            return this;
        }

        public DbDocumentBuilder fileType(EngineFileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public DbDocumentBuilder produceType(EngineTemplateType produceType) {
            this.produceType = produceType;
            return this;
        }

        public DbDocumentBuilder customTemplate(String customTemplate) {
            this.customTemplate = customTemplate;
            return this;
        }

        public DbDocumentBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public DbDocumentBuilder ignoreTableName(List<String> ignoreTableName) {
            this.ignoreTableName = ignoreTableName;
            return this;
        }

        public DbDocumentBuilder ignoreTablePrefix(List<String> ignoreTablePrefix) {
            this.ignoreTablePrefix = ignoreTablePrefix;
            return this;
        }

        public DbDocumentBuilder ignoreTableSuffix(List<String> ignoreTableSuffix) {
            this.ignoreTableSuffix = ignoreTableSuffix;
            return this;
        }

        public DbDocumentBuilder designatedTableName(List<String> designatedTableName) {
            this.designatedTableName = designatedTableName;
            return this;
        }

        public DbDocumentBuilder designatedTablePrefix(List<String> designatedTablePrefix) {
            this.designatedTablePrefix = designatedTablePrefix;
            return this;
        }

        public DbDocumentBuilder designatedTableSuffix(List<String> designatedTableSuffix) {
            this.designatedTableSuffix = designatedTableSuffix;
            return this;
        }

        public DbDocumentBuilder organization(String organization) {
            this.organization = organization;
            return this;
        }

        public DbDocumentBuilder organizationUrl(String organizationUrl) {
            this.organizationUrl = organizationUrl;
            return this;
        }

        public DbDocumentBuilder title(String title) {
            this.title = title;
            return this;
        }

        public DbDocumentBuilder version(String version) {
            this.version = version;
            return this;
        }

        public DbDocumentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DbDocumentBuilder generateSql(boolean generateSql) {
            this.generateSql = generateSql;
            return this;
        }

        DbDocumentBuilder() {
        }

        private DataSource createDataSource() {
            Assert.notNull(driverClassName, "driverClassName must not be null");
            Assert.notNull(jdbcUrl, "jdbcUrl must not be null");
            Assert.notNull(username, "username must not be null");
            Assert.notNull(password, "password must not be null");
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(driverClassName);
            hikariConfig.setJdbcUrl(jdbcUrl);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            // 设置可以获取tables remarks信息
            hikariConfig.addDataSourceProperty("useInformationSchema", "true");
            hikariConfig.setMinimumIdle(2);
            hikariConfig.setMaximumPoolSize(5);
            return new HikariDataSource(hikariConfig);
        }

        /**
         * 文档配置
         *
         * @param dataSource    数据源配置
         * @param engineConfig  引擎配置
         * @param processConfig 处理器配置
         * @return 输出文档
         */
        private Configuration createConfiguration(DataSource dataSource, EngineConfig engineConfig, ProcessConfig processConfig) {
            return Configuration.builder()
                    .title(title)
                    // 版本
                    .version(version)
                    // 描述
                    .description(description)
                    // 数据源
                    .dataSource(dataSource)
                    // 生成配置
                    .engineConfig(engineConfig)
                    // 生成配置
                    .produceConfig(processConfig)
                    .build();
        }

        /**
         * 生成表、筛选表规则配置
         *
         * @return 输出处理器配置
         */
        private ProcessConfig createProcessConfig() {
            return ProcessConfig.builder()
                    .ignoreTableName(ignoreTableName)
                    .ignoreTablePrefix(ignoreTablePrefix)
                    .ignoreTableSuffix(ignoreTableSuffix)
                    .designatedTableName(designatedTableName)
                    .designatedTablePrefix(designatedTablePrefix)
                    .designatedTableSuffix(designatedTableSuffix)
                    .build();
        }

        /**
         * 生成配置
         *
         * @return 输出引擎配置
         */
        private EngineConfig createEngineConfig() {
            Assert.notNull(fileOutputDir, "fileOutputDir must not be null");
            return EngineConfig.builder()
                    .fileOutputDir(fileOutputDir)
                    .openOutputDir(false)
                    .fileType(fileType)
                    .produceType(produceType)
                    .customTemplate(customTemplate)
                    .build();
        }


        public void build() {
            // 数据源
            DataSource dataSource = createDataSource();
            // 生成配置
            EngineConfig engineConfig = createEngineConfig();
            ProcessConfig processConfig = createProcessConfig();
            Configuration config = createConfiguration(dataSource, engineConfig, processConfig);
            // 输出文档生成
            new DocumentationExecute(config).execute();
            // 输出sql脚本
            if (generateSql) {
                createSql(dataSource, datasourceName, fileOutputDir, version);
            }
        }

        private void createSql(DataSource dataSource, String datasourceName, String fileOutputDir, String version) {
            Statement tmt = null;
            PreparedStatement pstmt = null;
            List<String> createSqlList = new ArrayList<>();
            String sql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = '" + datasourceName + "' and TABLE_TYPE = 'BASE TABLE'";
            try {
                tmt = dataSource.getConnection().createStatement();
                pstmt = dataSource.getConnection().prepareStatement(sql);
                ResultSet res = tmt.executeQuery(sql);
                while (res.next()) {
                    String tableName = res.getString(1);
                    if (tableName.contains("`")) {
                        continue;
                    }
                    if (ignoreTableName != null && ignoreTableName.contains(tableName)) {
                        continue;
                    }
                    boolean isContinue = false;
                    if (ignoreTablePrefix != null) {
                        for (String prefix : ignoreTablePrefix) {
                            if (tableName.startsWith(prefix)) {
                                isContinue = true;
                                break;
                            }
                        }
                    }
                    if (isContinue) {
                        continue;
                    }
                    if (ignoreTableSuffix != null) {
                        for (String suffix : ignoreTableSuffix) {
                            if (tableName.startsWith(suffix)) {
                                isContinue = true;
                                break;
                            }
                        }
                    }
                    if (isContinue) {
                        continue;
                    }
                    ResultSet rs = pstmt.executeQuery("show create Table `" + tableName + "`");

                    while (rs.next()) {
                        createSqlList.add("-- TableName: " + tableName + " \r\n");
                        createSqlList.add("DROP TABLE IF EXISTS '" + tableName + "'");
                        createSqlList.add(rs.getString(2));
                    }
                }

                String head = "-- 数据库建表语句 \r\n";
                head += "-- db:" + datasourceName + " version: " + version + "\r\n";
                String collect = String.join(";\r\n", createSqlList);
                collect = head + collect + ";";
                string2file(collect, fileOutputDir + "/" + datasourceName + "_" + version + ".sql");
            } catch (SQLException e) {
                // throw new RuntimeException(e);
            }
        }

        public static void string2file(String collect, String dirStr) {
            System.out.println("文件地址  " + dirStr);
            OutputStreamWriter osw = null;
            try {
                osw = new OutputStreamWriter(new FileOutputStream(new File(dirStr)), StandardCharsets.UTF_8);
                osw.write(collect);
                osw.flush();
            } catch (Exception e) {
                // skip
            } finally {
                if (osw != null) {
                    try {
                        osw.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
