package io.github.panxiaochao.database.metadata.enums;

import io.github.panxiaochao.database.metadata.core.sql.AbstractQuerySql;
import io.github.panxiaochao.database.metadata.core.sql.MySqlQuerySql;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@code DatabaseType}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-18
 */
@Getter
@AllArgsConstructor
public enum DatabaseType {

    /**
     * MYSQL
     */
    MYSQL("mysql", new MySqlQuerySql(), "MySql数据库");

    private final String dbName;

    private final AbstractQuerySql defaultQuery;

    private final String desc;

    public static final Map<String, DatabaseType> DATABASE_TYPE_MAP =
            Arrays.stream(DatabaseType.values()).collect(Collectors.toMap(DatabaseType::getDbName, Function.identity()));
}
