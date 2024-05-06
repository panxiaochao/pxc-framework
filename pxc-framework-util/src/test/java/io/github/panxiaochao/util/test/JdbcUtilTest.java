package io.github.panxiaochao.util.test;

import io.github.panxiaochao.core.utils.JdbcUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * </p>
 *
 * @author Lypxc
 * @since 2024-04-30
 * @version 1.0
 */
public class JdbcUtilTest {

	@Test
	void test() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// System.out.println(JdbcUtil.getDatabaseType(dataSource));
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/oauth2?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
			String username = "root";
			String password = "root123456";
			conn = JdbcUtil.getConnection(driver, url, username, password, hikariConfig -> {
				// 设置可以获取tables remarks信息
				hikariConfig.addDataSourceProperty("remarks", "true");
				hikariConfig.addDataSourceProperty("useInformationSchema", "true");
			});
			System.out.println(JdbcUtil.getDataBaseVersion(conn));
			ps = conn.prepareStatement("select * from sys_user");
			rs = ps.executeQuery();
			JdbcUtil.printResultSet(rs, true, ",");
			System.out.println(JdbcUtil.getResultSetValue(rs, 1));
		}
		catch (SQLException ex) {
			throw new RuntimeException( ex);
		}
		finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(ps);
			JdbcUtil.close(rs);
		}
	}

}
