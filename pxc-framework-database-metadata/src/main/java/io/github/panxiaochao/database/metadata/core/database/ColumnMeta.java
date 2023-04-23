package io.github.panxiaochao.database.metadata.core.database;

import io.github.panxiaochao.database.metadata.po.AbstractColumn;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code ColumnMeta}
 * <p> description: 数据库表 字段元数据
 *
 * @author Lypxc
 * @since 2023-04-21
 */
@Getter
@Setter
public class ColumnMeta extends AbstractColumn {

    private String schema;

    private String tableName;

    private boolean primaryKey;

    private boolean autoIncrement;

    private String columnName;

    private int ordinalPosition;

    private String columnDefault;

    private boolean nullable;

    private String dataType;

    private int columnLength;

    private int scale;

    private String columnType;

    private String columnComment;
}
