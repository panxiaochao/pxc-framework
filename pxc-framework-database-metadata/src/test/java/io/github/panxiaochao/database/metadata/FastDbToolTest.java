package io.github.panxiaochao.database.metadata;

import io.github.panxiaochao.database.metadata.builder.DefaultRulesBuilder;
import io.github.panxiaochao.database.metadata.po.ITable;
import io.github.panxiaochao.database.metadata.tool.FastDbTool;

import java.util.List;

/**
 * {@code FastDbToolTest}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-23
 */
public class FastDbToolTest {
    public static void main(String[] args) {
        FastDbTool fastDbTool = FastDbTool.create(
                "jdbc:mysql://localhost:3306/oauth2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                "root",
                "root123456"
        );
        fastDbTool.rulesBuilder(DefaultRulesBuilder.Builder::enableFillColumns);
        ITable table = fastDbTool.queryTables("sys_uers");
        List<ITable> tables = fastDbTool.queryTables();
    }
}
