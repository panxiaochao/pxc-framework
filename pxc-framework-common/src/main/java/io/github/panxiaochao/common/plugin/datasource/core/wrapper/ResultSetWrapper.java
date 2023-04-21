package io.github.panxiaochao.common.plugin.datasource.core.wrapper;

import io.github.panxiaochao.common.plugin.datasource.enums.DatabaseType;
import io.github.panxiaochao.common.utils.StringPoolUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code ResultSetWrapper}
 * <p> description: ResultSet包装类
 *
 * @author Lypxc
 * @since 2023-04-19
 */
@Getter
public class ResultSetWrapper {

    private final ResultSet resultSet;

    private final DatabaseType databaseType;

    public ResultSetWrapper(ResultSet resultSet, DatabaseType databaseType) {
        this.resultSet = resultSet;
        this.databaseType = databaseType;
    }

    public String getString(String columnLabel) {
        try {
            return resultSet.getString(columnLabel);
        } catch (SQLException sqlException) {
            throw new RuntimeException(String.format("读取[%s]字段出错!", columnLabel), sqlException);
        }
    }

    public int getInt(String columnLabel) {
        try {
            return resultSet.getInt(columnLabel);
        } catch (SQLException sqlException) {
            throw new RuntimeException(String.format("读取[%s]字段出错!", columnLabel), sqlException);
        }
    }

    /**
     * 获取格式化注释
     *
     * @param columnLabel 字段列
     * @return 注释
     */
    public String getComment(String columnLabel) {
        return StringUtils.isNotBlank(columnLabel) ? formatComment(getString(columnLabel)) : StringPoolUtil.EMPTY;
    }

    /**
     * @param comment 注释
     * @return 格式化内容
     */
    private String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? StringPoolUtil.EMPTY : comment.replaceAll("\r\n", "\t");
    }
}
