package io.github.panxiaochao.common.plugin.datasource.po;

/**
 * {@code IColumn}
 * <p> description: 数据库 字段接口
 *
 * @author Lypxc
 * @since 2023-04-14
 */
public interface IColumn {

    /**
     * 数据库 名
     *
     * @return String
     */
    String getSchema();

    /**
     * 数据库 表名
     *
     * @return String
     */
    String getTableName();

    /**
     * 是否主键
     *
     * @return boolean
     */
    boolean getPrimaryKey();

    /**
     * 是否自增
     *
     * @return boolean
     */
    boolean getAutoIncrement();

    /**
     * 字段 名称
     *
     * @return String
     */
    String getColumnName();

    /**
     * 表中的列的索引（从 1 开始）
     *
     * @return int
     */
    int getOrdinalPosition();

    /**
     * 字段默认值
     *
     * @return String
     */
    String getColumnDefault();

    /**
     * 是否空
     *
     * @return boolean
     */
    boolean getNullable();

    /**
     * 字段数据类型
     *
     * @return String
     */
    String getColumnDataType();

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
    int getColumnLength();

    /**
     * 针对数值类的精度
     *
     * @return int
     */
    int getScale();

    /**
     * 字段数据类型名
     *
     * @return String
     */
    String getColumnDataTypeName();

    /**
     * 字段注释
     *
     * @return String
     */
    String getColumnComment();
}
