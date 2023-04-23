package io.github.panxiaochao.database.metadata.core.database;

import io.github.panxiaochao.database.metadata.po.IColumn;
import io.github.panxiaochao.database.metadata.po.ITable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code TableMeta}
 * <p> description: 数字库表 元数据
 *
 * @author Lypxc
 * @since 2023-04-20
 */
@Getter
@Setter
public class TableMeta implements ITable {

    private String schema;

    private String tableName;

    private String tableComment;

    private LocalDateTime createTime;

    private List<? extends IColumn> columns;
}
