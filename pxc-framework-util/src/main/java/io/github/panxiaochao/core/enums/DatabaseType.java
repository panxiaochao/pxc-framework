package io.github.panxiaochao.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 数据库类型枚举.
 * </p>
 *
 * @author Lypxc
 * @since 2023-11-14
 */
@Getter
@AllArgsConstructor
public enum DatabaseType {

	/**
	 * MYSQL
	 */
	MYSQL("mysql", "MySql数据库"),
	/**
	 * MARIADB
	 */
	MARIADB("mariadb", "MariaDB数据库"),
	/**
	 * ORACLE
	 */
	ORACLE("oracle", "Oracle11g及以下数据库(高版本推荐使用ORACLE_NEW)"),
	/**
	 * oracle12c new pagination
	 */
	ORACLE_12C("oracle12c", "Oracle12c+数据库"),
	/**
	 * DB2
	 */
	DB2("db2", "DB2数据库"),
	/**
	 * H2
	 */
	H2("h2", "H2数据库"),
	/**
	 * HSQL
	 */
	HSQL("hsql", "HSQL数据库"),
	/**
	 * SQLITE
	 */
	SQLITE("sqlite", "SQLite数据库"),
	/**
	 * POSTGRE
	 */
	POSTGRE_SQL("postgresql", "Postgre数据库"),
	/**
	 * SQLSERVER2005
	 */
	SQL_SERVER2005("sqlserver2005", "SQLServer2005数据库"),
	/**
	 * SQLSERVER
	 */
	SQL_SERVER("sqlserver", "SQLServer数据库"),
	/**
	 * DM
	 */
	DM("dm", "达梦数据库"),
	/**
	 * xugu
	 */
	XU_GU("xugu", "虚谷数据库"),
	/**
	 * Kingbase
	 */
	KINGBASE_ES("kingbasees", "人大金仓数据库"),
	/**
	 * Phoenix
	 */
	PHOENIX("phoenix", "Phoenix HBase数据库"),
	/**
	 * Gauss
	 */
	GAUSS("zenith", "Gauss 数据库"),
	/**
	 * ClickHouse
	 */
	CLICK_HOUSE("clickhouse", "clickhouse 数据库"),
	/**
	 * GBase
	 */
	GBASE("gbase", "南大通用(华库)数据库"),
	/**
	 * GBase-8s
	 */
	GBASE_8S("gbase-8s", "南大通用数据库 GBase 8s"),
	/**
	 * Sinodb
	 */
	SINODB("sinodb", "星瑞格数据库"),
	/**
	 * Oscar
	 */
	OSCAR("oscar", "神通数据库"),
	/**
	 * Sybase
	 */
	SYBASE("sybase", "Sybase ASE 数据库"),
	/**
	 * OceanBase
	 */
	OCEAN_BASE("oceanbase", "OceanBase 数据库"),
	/**
	 * Firebird
	 */
	FIREBIRD("Firebird", "Firebird 数据库"),
	/**
	 * HighGo
	 */
	HIGH_GO("highgo", "瀚高数据库"),
	/**
	 * CUBRID
	 */
	CUBRID("cubrid", "CUBRID数据库"),
	/**
	 * GOLDILOCKS
	 */
	GOLDILOCKS("goldilocks", "GOLDILOCKS数据库"),
	/**
	 * CSIIDB
	 */
	CSIIDB("csiidb", "CSIIDB数据库"),
	/**
	 * Hana
	 */
	SAP_HANA("hana", "SAP_HANA数据库"),
	/**
	 * Impala
	 */
	IMPALA("impala", "impala数据库"),
	/**
	 * Vertica
	 */
	VERTICA("vertica", "vertica数据库"),
	/**
	 * xcloud
	 */
	XCloud("xcloud", "行云数据库"),
	/**
	 * redshift
	 */
	REDSHIFT("redshift", "亚马逊redshift数据库"),
	/**
	 * openGauss
	 */
	OPENGAUSS("openGauss", "华为 opengauss 数据库"),
	/**
	 * TDengine
	 */
	TDENGINE("TDengine", "TDengine数据库"),
	/**
	 * Informix
	 */
	INFORMIX("informix", "Informix数据库"),
	/**
	 * uxdb
	 */
	UXDB("uxdb", "优炫数据库"),
	/**
	 * lealone
	 */
	LEALONE("lealone", "Lealone数据库"),
	/**
	 * UNKNOWN DB
	 */
	OTHER("other", "其他数据库");

	/**
	 * 数据库名称
	 */
	private final String db;

	/**
	 * 描述
	 */
	private final String desc;

	/**
	 * 获取数据库类型
	 * @param dbType 数据库类型字符串
	 */
	public static DatabaseType getDatabaseType(String dbType) {
		for (DatabaseType type : DatabaseType.values()) {
			if (type.db.equalsIgnoreCase(dbType)) {
				return type;
			}
		}
		return OTHER;
	}

}
