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
package io.github.panxiaochao.core.exception;

import io.github.panxiaochao.core.ienums.IEnum;
import lombok.Getter;

/**
 * <p>
 * 基础运行时异常类
 * </p>
 *
 * @author Lypxc
 * @since 2022/4/19
 */
@Getter
public class ServerRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -2307670685197783604L;

	/**
	 * 错误码
	 */
	private final int code;

	public ServerRuntimeException(IEnum<Integer> responseEnum) {
		super(responseEnum.getMessage());
		this.code = responseEnum.getCode();
	}

	public ServerRuntimeException(IEnum<Integer> responseEnum, String message) {
		super(message);
		this.code = responseEnum.getCode();
	}

	public ServerRuntimeException(IEnum<Integer> responseEnum, Throwable cause) {
		super(responseEnum.getMessage(), cause);
		this.code = responseEnum.getCode();
	}

	public ServerRuntimeException(IEnum<Integer> responseEnum, String message, Throwable cause) {
		super(message, cause);
		this.code = responseEnum.getCode();
	}

}
