package io.github.panxiaochao.core.utils.meta.ddl.impl;

import io.github.panxiaochao.core.enums.DatabaseType;
import io.github.panxiaochao.core.utils.StrUtil;
import io.github.panxiaochao.core.utils.meta.db.ColumnMeta;
import io.github.panxiaochao.core.utils.meta.ddl.IDatabase;
import org.apache.commons.lang3.StringUtils;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * MySql 数据库实现类
 * </p>
 *
 * @author lypxc
 * @since 2025-06-13
 * @version 1.0
 */
public class DatabaseMySqlImpl implements IDatabase {

	private static final String SHOW_CREATE_TABLE_SQL = "SHOW CREATE TABLE `%s`.`%s` ";

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.MYSQL;
	}

	/**
	 * 生成创建表 DDL
	 * @return 创建表 DDL-SQL
	 */
	@Override
	public String generateCreateTableSql(String schemaName, String tableName, String tableComment,
			List<ColumnMeta> columnMetas) {
		List<String> ddlCommands = new ArrayList<>();
		// 获取主键数组
		List<String> pks = columnMetas.stream()
			.filter(ColumnMeta::isPrimaryKey)
			.map(ColumnMeta::getColumnName)
			.collect(Collectors.toList());
		ddlCommands.add("CREATE TABLE");
		ddlCommands.add(getQuotedSchemaTableCombination(schemaName, tableName));
		ddlCommands.add("(");
		for (int i = 0; i < columnMetas.size(); i++) {
			if (i > 0) {
				ddlCommands.add(",");
			}
			else {
				ddlCommands.add("");
			}
			ColumnMeta v = columnMetas.get(i);
			ddlCommands.add(reflectionFieldSqlFromColumnMeta(v));
		}
		ddlCommands.add(appendPrimaryKeyForCreateTableSql(pks));
		ddlCommands.add(")");
		ddlCommands.add("ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci");
		if (StrUtil.isNotBlank(tableComment)) {
			ddlCommands.add("COMMENT='" + tableComment + "'");
		}
		return String.join(" ", ddlCommands);
	}

	public String reflectionFieldSqlFromColumnMeta(ColumnMeta column) {
		int type = column.getJdbcType();
		List<String> columnDdl = new ArrayList<>();
		columnDdl.add("`" + column.getColumnName() + "`");
		// jdbcType 转换
		getJdbcType(column, type, columnDdl);
		if (!column.isNullable()) {
			columnDdl.add("NOT NULL");
		}
		else if (!column.isPrimaryKey()) {
			columnDdl.add("DEFAULT NULL");
		}
		// 主键 && 自增
		if (column.isPrimaryKey() && column.isAutoIncrement()) {
			columnDdl.add("AUTO_INCREMENT");
		}
		// 默认值
		if (StrUtil.isNotBlank(column.getColumnDefault()) && !"null".equals(column.getColumnDefault())
				&& !"NULL".equals(column.getColumnDefault())) {
			if (type != Types.TIMESTAMP && type != Types.TIME && type != Types.DATE) {
				if (column.getColumnDefault().startsWith("'")) {
					columnDdl.add("DEFAULT " + column.getColumnDefault());
				}
				else {
					columnDdl.add("DEFAULT '" + column.getColumnDefault() + "'");
				}
			}
			else {
				columnDdl.add("DEFAULT CURRENT_TIMESTAMP");
			}
		}
		// 注释
		if (StrUtil.isNotBlank(column.getColumnComment())) {
			columnDdl.add(String.format("COMMENT '%s' ", column.getColumnComment().replace("'", "\\'")));
		}

		return String.join(" ", columnDdl);
	}

	private void getJdbcType(ColumnMeta column, int type, List<String> columnDdl) {
		switch (type) {
			// 数值类型
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
			case Types.BIGINT:
				if (type == Types.TINYINT && column.getColumnLength() == 1) {
					columnDdl.add("TINYINT(1)");
				}
				else {
					columnDdl.add(column.getJdbcTypeName());
				}
				break;
			// 浮点类型
			case Types.REAL:
			case Types.FLOAT:
			case Types.DOUBLE:
			case Types.NUMERIC:
			case Types.DECIMAL:
				if (column.getColumnLength() > 0 && column.getScale() > 0) {
					if (column.getColumnLength() >= column.getScale()) {
						columnDdl.add(column.getJdbcTypeName() + "(" + column.getColumnLength() + ","
								+ column.getScale() + ")");
					}
					else {
						throw new RuntimeException(column.getColumnName() + " 字段长度不能小于精度");
					}
				}
				else if (column.getColumnLength() > 0) {
					columnDdl.add(column.getJdbcTypeName());
				}
				break;
			// 日期类型
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
			case Types.TIMESTAMP_WITH_TIMEZONE:
				columnDdl.add(column.getJdbcTypeName());
				break;
			// 字符串类型
			case Types.CHAR:
			case Types.NCHAR:
			case Types.VARCHAR:
			case Types.NVARCHAR:
				if ("ENUM".equalsIgnoreCase(column.getJdbcTypeName())
						|| "SET".equalsIgnoreCase(column.getJdbcTypeName())) {
					columnDdl.add(column.getJdbcTypeName() + "('" + column.getColumnLength() + "')");
				}
				else if ("TINYTEXT".equalsIgnoreCase(column.getJdbcTypeName())) {
					columnDdl.add(column.getJdbcTypeName());
				}
				else {
					columnDdl.add(column.getJdbcTypeName() + "(" + column.getColumnLength() + ")");
				}
				break;
			case Types.LONGVARCHAR:
			case Types.LONGNVARCHAR:
			case Types.NCLOB:
			case Types.CLOB:
			case Types.BLOB:
			case Types.LONGVARBINARY:
			case Types.VARBINARY:
			case Types.SQLXML:
			case Types.ROWID:
			case Types.BINARY:
				columnDdl.add(column.getJdbcTypeName());
				break;
			// 布尔类型
			case Types.BIT:
			case Types.BOOLEAN:
				if (column.getColumnLength() == 1) {
					columnDdl.add("TINYINT(1)");
				}
				else {
					columnDdl.add(column.getJdbcTypeName() + "(" + column.getColumnLength() + ")");
				}
				break;
			default:
				columnDdl.add(column.getJdbcTypeName() + "(" + column.getColumnLength() + ")");
		}
	}

	public String appendPrimaryKeyForCreateTableSql(List<String> pks) {
		// 检查输入列表是否为 null 或为空
		if (pks == null || pks.isEmpty()) {
			return "";
		}
		String joinedPkColumns = "`" + StringUtils.join(pks, "` , `") + "`";
		StringBuilder sqlBuilder = new StringBuilder();
		// 构建主键列定义部分
		sqlBuilder.append(", PRIMARY KEY (").append(joinedPkColumns).append(")");
		// 多主键时使用 BTREE 索引
		if (pks.size() > 1) {
			sqlBuilder.append(" USING BTREE");
		}
		return sqlBuilder.toString();
	}

	/**
	 * 获取数据库的表全名
	 * @param schemaName 模式名称
	 * @param tableName 表名称
	 * @return 表全名
	 */
	@Override
	public String getQuotedSchemaTableCombination(String schemaName, String tableName) {
		if (StrUtil.isBlank(schemaName)) {
			return String.format("`%s`", tableName);
		}
		return String.format("`%s`.`%s`", schemaName, tableName);
	}

}
