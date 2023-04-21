package io.github.panxiaochao.common.plugin.datasource.po;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code ITable}
 * <p> description: 数据库 表接口
 *
 * @author Lypxc
 * @since 2023-04-14
 */
@Schema(description = "数据库表")
public interface ITable {

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
     * 表注释
     *
     * @return String
     */
    @Schema(description = "表注释")
    String getTableComment();

    /**
     * 表创建时间
     *
     * @return LocalDateTime
     */
    @Schema(description = "表创建时间")
    LocalDateTime getCreateTime();

    /**
     * 数据库 字段数组
     *
     * @return the list of columns
     */
    @Schema(description = "数据库字段数组")
    List<? extends IColumn> getColumns();
}
