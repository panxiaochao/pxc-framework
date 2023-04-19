package io.github.panxiaochao.common.plugin.datasource.po;

/**
 * {@code AbstractColumn}
 * <p> description: 数据库 字段接口 抽象类
 *
 * @author Lypxc
 * @since 2023-04-19
 */
public abstract class AbstractColumn implements IColumn {

    private String schema;

    private String tableName;

    private boolean primaryKey;

    private boolean autoIncrement;

    private String columnName;

    private int ordinalPosition;

    private String columnDefault;

    private boolean nullable;

    private String columnDataType;

    private int columnLength;

    private int scale;

    private String columnDataTypeName;

    private String columnComment;

    /**
     * 数据库 名
     *
     * @return String
     */
    @Override
    public String getSchema() {
        return this.schema;
    }

    /**
     * 数据库 表名
     *
     * @return String
     */
    @Override
    public String getTableName() {
        return this.tableName;
    }

    /**
     * 是否主键
     *
     * @return boolean
     */
    @Override
    public boolean getPrimaryKey() {
        return this.primaryKey;
    }

    /**
     * 是否自增
     *
     * @return boolean
     */
    @Override
    public boolean getAutoIncrement() {
        return this.autoIncrement;
    }

    /**
     * 字段 名称
     *
     * @return String
     */
    @Override
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * 表中的列的索引（从 1 开始）
     *
     * @return int
     */
    @Override
    public int getOrdinalPosition() {
        return this.ordinalPosition;
    }

    /**
     * 字段默认值
     *
     * @return String
     */
    @Override
    public String getColumnDefault() {
        return this.columnDefault;
    }

    /**
     * 是否空
     *
     * @return boolean
     */
    @Override
    public boolean getNullable() {
        return this.nullable;
    }

    /**
     * 字段数据类型
     *
     * @return String
     */
    @Override
    public String getColumnDataType() {
        return this.columnDataType;
    }

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
    @Override
    public int getColumnLength() {
        return this.columnLength;
    }

    /**
     * 针对数值类的精度
     *
     * @return int
     */
    @Override
    public int getScale() {
        return this.scale;
    }

    /**
     * 字段数据类型名
     *
     * @return String
     */
    @Override
    public String getColumnDataTypeName() {
        return this.columnDataTypeName;
    }

    /**
     * 字段注释
     *
     * @return String
     */
    @Override
    public String getColumnComment() {
        return this.columnComment;
    }
}
