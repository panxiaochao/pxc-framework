/*
 * Copyright © 2025-2026 Lypxc (545685602@qq.com)
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

/**
 * <p>
 * </p>
 *
 * @author lypxc
 * @since 2025-06-16
 * @version 1.0
 */
public class ColumnTypes {

	/**
	 * Value type indicating that the value has no type set
	 */
	public static final int TYPE_NONE = 0;

	/**
	 * Value type indicating that the value contains a floating point double precision
	 * number.
	 */
	public static final int TYPE_NUMBER = 1;

	/**
	 * Value type indicating that the value contains a text String.
	 */
	public static final int TYPE_STRING = 2;

	/**
	 * Value type indicating that the value contains a Date.
	 */
	public static final int TYPE_DATE = 3;

	/**
	 * Value type indicating that the value contains a boolean.
	 */
	public static final int TYPE_BOOLEAN = 4;

	/**
	 * Value type indicating that the value contains a long integer.
	 */
	public static final int TYPE_INTEGER = 5;

	/**
	 * Value type indicating that the value contains a floating point precision number
	 * with arbitrary precision.
	 */
	public static final int TYPE_BIGNUMBER = 6;

	/**
	 * Value type indicating that the value contains an Object.
	 */
	public static final int TYPE_SERIALIZABLE = 7;

	/**
	 * Value type indicating that the value contains binary data: BLOB, CLOB, ...
	 */
	public static final int TYPE_BINARY = 8;

	/**
	 * Value type indicating that the value contains a date-time with nanosecond precision
	 */
	public static final int TYPE_TIMESTAMP = 9;

	/**
	 * Value type indicating that the value contains a time
	 */
	public static final int TYPE_TIME = 10;

	/**
	 * Value type indicating that the value contains a Internet address
	 */
	public static final int TYPE_INET = 11;

	public static int transformJdbcType(int jdbcType) {
		int resultType;
		switch (jdbcType) {
			case java.sql.Types.CHAR:
			case java.sql.Types.NCHAR:
			case java.sql.Types.VARCHAR:
			case java.sql.Types.NVARCHAR:
				resultType = ColumnTypes.TYPE_STRING;
				break;
			case java.sql.Types.LONGVARCHAR:
			case java.sql.Types.LONGNVARCHAR:
			case java.sql.Types.CLOB:
			case java.sql.Types.NCLOB:
			case java.sql.Types.SQLXML:
			case java.sql.Types.ROWID:
				resultType = ColumnTypes.TYPE_STRING;
				break;
			case java.sql.Types.BIGINT:
				resultType = ColumnTypes.TYPE_BIGNUMBER;
				break;
			case java.sql.Types.INTEGER:
            case java.sql.Types.TINYINT:
            case java.sql.Types.SMALLINT:
                resultType = ColumnTypes.TYPE_INTEGER;
				break;
            case java.sql.Types.DECIMAL:
			case java.sql.Types.DOUBLE:
			case java.sql.Types.FLOAT:
			case java.sql.Types.REAL:
			case java.sql.Types.NUMERIC:
				resultType = ColumnTypes.TYPE_NUMBER;
				break;
			case java.sql.Types.TIMESTAMP:
			case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
				resultType = ColumnTypes.TYPE_TIMESTAMP;
				break;
			case java.sql.Types.DATE:
				resultType = ColumnTypes.TYPE_DATE;
				break;
			case java.sql.Types.TIME:
			case java.sql.Types.TIME_WITH_TIMEZONE:
				resultType = ColumnTypes.TYPE_TIME;
				break;
			case java.sql.Types.BOOLEAN:
			case java.sql.Types.BIT:
				resultType = ColumnTypes.TYPE_BOOLEAN;
				break;
			case java.sql.Types.BINARY:
			case java.sql.Types.BLOB:
			case java.sql.Types.VARBINARY:
			case java.sql.Types.LONGVARBINARY:
				resultType = ColumnTypes.TYPE_BINARY;
				break;
			default:
				resultType = ColumnTypes.TYPE_STRING;
				break;
		}
		return resultType;
	}

}