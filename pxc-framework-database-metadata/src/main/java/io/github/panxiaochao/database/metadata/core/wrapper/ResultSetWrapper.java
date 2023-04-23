package io.github.panxiaochao.database.metadata.core.wrapper;

import io.github.panxiaochao.common.utils.StringPoolUtil;
import io.github.panxiaochao.database.metadata.enums.DatabaseType;
import lombok.Getter;
import org.springframework.util.StringUtils;

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
            return StringUtils.hasText(columnLabel) ? resultSet.getString(columnLabel) : StringPoolUtil.EMPTY;
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
        return StringUtils.hasText(columnLabel) ? formatComment(getString(columnLabel)) : StringPoolUtil.EMPTY;
    }

    /**
     * @param comment 注释
     * @return 格式化内容
     */
    private String formatComment(String comment) {
        return StringUtils.hasText(comment) ? comment.replaceAll("\r\n", "\t") : StringPoolUtil.EMPTY;
    }
}