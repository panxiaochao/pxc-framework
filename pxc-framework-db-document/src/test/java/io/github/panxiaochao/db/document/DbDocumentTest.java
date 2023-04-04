package io.github.panxiaochao.db.document;

import cn.smallbun.screw.core.engine.EngineFileType;
import io.github.panxiaochao.db.document.generator.PxcDbDocumentGenerator;

/**
 * {@code DbDocumentTest}
 * <p> description:
 *
 * @author Lypxc
 * @since 2022-12-28
 */
public class DbDocumentTest {
    public static void main(String[] args) {
        PxcDbDocumentGenerator.INSTANCE()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .jdbcUrl("jdbc:mysql://localhost:3306/oauth2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowMultiQueries=true")
                .datasourceName("oauth2")
                .username("root")
                .password("root123456")
                .fileType(EngineFileType.HTML)
                .fileOutputDir("/Users/Lypxc/Desktop")
                .generateSql(false)
                .build();
    }
}
