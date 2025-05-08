package io.github.panxiaochao.operate.log.core.enums;

import lombok.Getter;

/**
 * <p>
 * 操作日志状态枚举
 * </p>
 *
 * @author Lypxc
 * @since 2025-05-08
 * @version 1.0
 */
@Getter
public enum OperateLogStatus {

	/**
	 * 成功
	 */
	SUCCESS(1),

	/**
	 * 失败
	 */
	FAIL(0);

	private final int code;

	OperateLogStatus(int code) {
		this.code = code;
	}

}
