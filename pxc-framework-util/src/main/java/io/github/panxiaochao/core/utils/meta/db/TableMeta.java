/*
 * Copyright © 2022-2024 Lypxc (545685602@qq.com)
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
package io.github.panxiaochao.core.utils.meta.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 数据库表-元数据
 * </p>
 *
 * @author Lypxc
 * @since 2024-05-07
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class TableMeta implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据库 名
	 */
	private String schema;

	/**
	 * 数据库 目录
	 */
	private String catalog;

	/**
	 * 数据库 表名
	 */
	private String tableName;

	/**
	 * 表 注释
	 */
	private String tableComment;

	/**
	 * 表 类型
	 */
	private String tableType;

	/**
	 * 主键字段名列表
	 */
	private Set<String> pkNames = new LinkedHashSet<>();

	/**
	 * 索引信息
	 */
	private List<IndexMeta> indexInfoList = new LinkedList<>();

	/**
	 * 数据库 字段对象
	 */
	private Map<String, ColumnMeta> columns = new LinkedHashMap<>();

	/**
	 * 构建数据表元数据
	 */
	public static TableMeta build(ResultSet rs) {
		TableMeta table = new TableMeta();
		try {
			table.setCatalog(rs.getString("TABLE_CAT"));
			table.setSchema(rs.getString("TABLE_SCHEM"));
			table.setTableName(rs.getString("TABLE_NAME"));
			table.setTableComment(rs.getString("REMARKS"));
			table.setTableType(rs.getString("TABLE_TYPE"));
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return table;
	}

	/**
	 * 是否是主键
	 * @param columnName 字段名
	 * @return true or false
	 */
	public boolean isPrimaryKey(String columnName) {
		return getPkNames().contains(columnName);
	}

}
