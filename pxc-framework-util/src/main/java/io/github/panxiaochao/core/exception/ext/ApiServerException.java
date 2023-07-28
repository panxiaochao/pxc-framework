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
package io.github.panxiaochao.core.exception.ext;

import io.github.panxiaochao.core.exception.ServerException;
import io.github.panxiaochao.core.ienums.IEnum;

/**
 * {@code ApiServerException}
 * <p>
 * description: Api and Server exception
 *
 * @author Lypxc
 * @since 2022-11-28
 */
public class ApiServerException extends ServerException {

	private static final long serialVersionUID = -4367714276298639594L;

	public ApiServerException() {
	}

	public ApiServerException(IEnum<Integer> responseEnum, String message) {
		super(responseEnum, null, message);
	}

	public ApiServerException(IEnum<Integer> responseEnum, String message, Throwable cause) {
		super(responseEnum, null, message, cause);
	}

	public ApiServerException(IEnum<Integer> responseEnum, Object[] args, String message) {
		super(responseEnum, args, message);
	}

	public ApiServerException(IEnum<Integer> responseEnum, Object[] args, String message, Throwable cause) {
		super(responseEnum, args, message, cause);
	}

	public ApiServerException(Integer code, String errMsg) {
		super(code, errMsg);
	}

	public ApiServerException(Integer code, String errMsg, Throwable cause) {
		super(code, errMsg, cause);
	}

}
