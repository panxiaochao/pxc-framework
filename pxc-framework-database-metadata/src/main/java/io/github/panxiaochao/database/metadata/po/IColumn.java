package io.github.panxiaochao.database.metadata.po;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * {@code IColumn}
 * <p> description: 数据库 字段接口
 *
 * @author Lypxc
 * @since 2023-04-14
 */
@Schema(description = "数据库字段")
public interface IColumn {

    /**
     * 数据库 名
     *
     * @return String
     */
    @Schema(description = "数据库名")
    String getSchema();

    /**
     * 数据库 表名
     *
     * @return String
     */
    @Schema(description = "表名")
    String getTableName();

    /**
     * 是否主键
     *
     * @return boolean
     */
    @Schema(description = "是否主键")
    boolean isPrimaryKey();

    /**
     * 是否自增
     *
     * @return boolean
     */
    @Schema(description = "是否自增")
    boolean isAutoIncrement();

    /**
     * 字段 名称
     *
     * @return String
     */
    @Schema(description = "字段名称")
    String getColumnName();

    /**
     * 表中的列的索引（从 1 开始）
     *
     * @return int
     */
    @Schema(description = "列的索引")
    int getOrdinalPosition();

    /**
     * 字段默认值
     *
     * @return String
     */
    @Schema(description = "字段默认值")
    String getColumnDefault();

    /**
     * 是否空
     *
     * @return boolean
     */
    @Schema(description = "是否空")
    boolean isNullable();

    /**
     * 字段数据类型
     *
     * @return String
     */
    @Schema(description = "字段数据类型")
    String getDataType();

    /**
     * 获取列长度
     * <pre>
     * 1.对于数值数据，这是最大精度。
     * 2.对于字符数据，这是字符长度。
     * 3.对于日期时间数据类型，这是 String 表示形式的字符长度（假定允许的最大小数秒组件的精度）。
     * 4.对于二进制数据，这是字节长度。
     * </pre>
     *
     * @return int
     */
    @Schema(description = "字段长度")
    int getColumnLength();

    /**
     * 针对数值类的精度
     *
     * @return int
     */
    @Schema(description = "字段精度")
    int getScale();

    /**
     * 字段数据类型, 全称
     *
     * @return String
     */
    @Schema(description = "字段数据类型，包含精度")
    String getColumnType();

    /**
     * 字段注释
     *
     * @return String
     */
    @Schema(description = "字段注释")
    String getColumnComment();
}
