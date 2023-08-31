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
package io.github.panxiaochao.sensitive.strategy;

/**
 * <p>
 * 脱敏策略抽象类
 * </p>
 *
 * @author Lypxc
 * @since 2023-08-31
 */
public class AbstractFSensitiveStrategy implements IFSensitiveStrategy {

	public AbstractFSensitiveStrategy() {
	}

	/**
	 * 自定义处理，需要继承改造
	 * @param jsonValue json value 值
	 * @return 脱敏值
	 */
	@Override
	public String handler(String jsonValue) {
		return jsonValue;
	}

}
