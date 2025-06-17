package io.github.panxiaochao.core.utils.meta.ddl;

import io.github.panxiaochao.core.enums.DatabaseType;
import io.github.panxiaochao.core.utils.meta.db.ColumnMeta;

import java.util.List;

/**
 * <p>
 * 数据库 元接口
 * </p>
 *
 * @author lypxc
 * @since 2025-06-13
 * @version 1.0
 */
public interface IDatabase {

	/**
	 * 获取数据库类型
	 * @return 数据库类型
	 */
	DatabaseType getDatabaseType();

	/**
	 * 生成创建表 DDL
	 * @return 创建表 DDL-SQL
	 */
	String generateCreateTableSql(String schemaName, String tableName, String tableComment,
			List<ColumnMeta> columnMetas);

	/**
	 * 获取数据库的表全名
	 * @param schemaName 模式名称
	 * @param tableName 表名称
	 * @return 表全名
	 */
	String getQuotedSchemaTableCombination(String schemaName, String tableName);

}
