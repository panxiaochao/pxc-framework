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
package io.github.panxiaochao.core.utils.sysinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * SysInfo Entity
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-07
 */
@Setter
@Getter
@ToString
public class SysInfo {

	/**
	 * 服务器名称
	 */
	private String computerName;

	/**
	 * 服务器Ip
	 */
	private String computerIp;

	/**
	 * DNS
	 */
	private String dns;

	/**
	 * IPV4网关
	 */
	private String ipv4Gateway;

	/**
	 * IPV6网关
	 */
	private String ipv6Gateway;

	/**
	 * 项目路径
	 */
	private String userDir;

	/**
	 * 操作系统
	 */
	private String osName;

	/**
	 * 系统架构
	 */
	private String osArch;

}
