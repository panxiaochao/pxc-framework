package io.github.panxiaochao.database.metadata.core.sql;

import io.github.panxiaochao.database.metadata.api.IQuerySql;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

/**
 * {@code AbstractQuerySql}
 * <p> description: 数据库 查询抽象类
 *
 * @author Lypxc
 * @since 2023-04-14
 */
@Getter
public abstract class AbstractQuerySql implements IQuerySql {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean isAutoIncrement(ResultSet resultSet) {
        return false;
    }

    @Override
    public boolean isPrimaryKey(ResultSet resultSet) {
        return false;
    }
}
