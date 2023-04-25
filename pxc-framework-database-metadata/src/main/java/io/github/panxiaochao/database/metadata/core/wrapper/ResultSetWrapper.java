/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
