package io.github.panxiaochao.common.plugin.datasource.core.database;

import io.github.panxiaochao.common.plugin.datasource.po.IColumn;
import io.github.panxiaochao.common.plugin.datasource.po.ITable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code TableMeta}
 * <p> description:
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
