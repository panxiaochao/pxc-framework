package io.github.panxiaochao.core.utils.meta.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 数据库-字段元数据
 * </p>
 *
 * @author Lypxc
 * @since 2024-05-07
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class ColumnMeta implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据库 名
	 */
	private String schema;

	/**
	 * 数据库 表名
	 */
	private String tableName;

	/**
	 * 字段 名称
	 */
	private String columnName;

	/**
	 * 是否 主键
	 */
	private boolean primaryKey;

	/**
	 * 是否 自增
	 */
	private boolean autoIncrement;

	/**
	 * 表中的列的索引（从 1 开始）
	 */
	private int ordinalPosition;

	/**
	 * 字段 默认值
	 */
	private String columnDefault;

	/**
	 * 是否 可空
	 */
	private boolean nullable;

	/**
	 * jdbc类型，对应java.sql.Types中的类型
	 */
	private int jdbcType;

	/**
	 * jdbc类型名
	 */
	private String jdbcTypeName;

	/**
	 * 字段 长度
	 * <p>
	 * <pre>
	 * 1.对于数值数据，这是最大精度。
	 * 2.对于字符数据，这是字符长度。
	 * 3.对于日期时间数据类型，这是 String 表示形式的字符长度（假定允许的最大小数秒组件的精度）。
	 * 4.对于二进制数据，这是字节长度。
	 * </pre>
	 * </p>
	 */
	private int columnLength;

	/**
	 * 针对数值类的精度
	 */
	private int scale;

	/**
	 * 字段 注释
	 */
	private String columnComment;

}
