package io.github.panxiaochao.operate.log.core.enums;

/**
 * <p>
 * 业务类型枚举
 * </p>
 *
 * @author Lypxc
 * @since 2025-05-08
 * @version 1.0
 */
public enum BusinessType {

	/**
	 * 新增
	 */
	INSERT,
	/**
	 * 修改
	 */
	UPDATE,
	/**
	 * 删除
	 */
	DELETE,
	/**
	 * 查询
	 */
	QUERY,
	/**
	 * 授权
	 */
	GRANT,
	/**
	 * 导出
	 */
	EXPORT,
	/**
	 * 导入
	 */
	IMPORT,
	/**
	 * 登录
	 */
	LOGIN,
	/**
	 * 登出
	 */
	LOGOUT,
	/**
	 * 强退
	 */
	FORCE_LOGOUT,
	/**
	 * 生成代码
	 */
	GENERATE_CODE,
	/**
	 * 清空数据
	 */
	CLEAN,
	/**
	 * 其它
	 */
	OTHER

}
