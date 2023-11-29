package io.github.panxiaochao.generate.test;

import io.github.panxiaochao.generate.enums.GenerateDbType;
import io.github.panxiaochao.generate.tool.PxcMybatisPlusGeneratorTools;

/**
 * {@code PxcMybatisPlusGeneratorTest}
 * <p>
 * description:
 *
 * @author Lypxc
 * @since 2023-02-15
 */
public class PxcMybatisPlusGeneratorTest {

	public static void main(String[] args) {
		PxcMybatisPlusGeneratorTools.builder()
			// .jdbcUrl("jdbc:mysql://localhost:3308/oauth2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai")
			.jdbcUrl(
					"jdbc:mysql://localhost:3306/pxc-system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai")
			.username("root")
			.password("root123456")
			.dbType(GenerateDbType.MYSQL)
			// .outputDir("E:/work_2023/test")
			.outputDir("/Users/Lypxc/Documents/project/generate_pxc")
			.parent("io.github.panxiaochao")
			.moduleName("system")
			.entityName("po")
			.insertFields("create_time")
			.updateFields("update_time")
			.build();
	}

}
