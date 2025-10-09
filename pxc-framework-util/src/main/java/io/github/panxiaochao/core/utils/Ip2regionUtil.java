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
package io.github.panxiaochao.core.utils;

import io.github.panxiaochao.core.utils.ipregion.Ip2RegionLoader;
import io.github.panxiaochao.core.utils.ipregion.IpInfo;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * <p>
 * Ip2region 工具类
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-10
 */
public class Ip2regionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(Ip2regionUtil.class);

	/**
	 * ip2region_v4.xdb 搜索对象
	 */
	private static final Searcher SEARCHER_V4 = Ip2RegionLoader.searcherV4();

	/**
	 * ip2region_v6.xdb 搜索对象
	 */
	private static final Searcher SEARCHER_V6 = Ip2RegionLoader.searcherV6();

	/**
	 * IP解析, 返回<code>IpInfo</code>对象
	 * @param ip 解析的ip
	 * @return IpInfo
	 */
	public static IpInfo memorySearch(String ip) {
		try {
			String[] ipV4Part = IpInfo.getIpv4Part(ip);
			if (ipV4Part.length == 4) {
				IpInfo ipInfo = IpInfo.toIpInfo(SEARCHER_V4.search(ip));
				ipInfo.setIp(ip);
				return ipInfo;
			}
			else if (ip.contains(":")) {
				// IpInfo ipInfo = IpInfo.toIpInfo(SEARCHER_V6.search(ip));
				// ipInfo.setIp(ip);
				LOGGER.error("不支持 IPv6 地址, 请自定义实现或采用V3版本自定义模块！");
				return null;
			}
			else {
				// 3.不合法 IP
				LOGGER.error("invalid ip address {}", ip);
			}
			return null;
		}
		catch (Exception e) {
			LOGGER.error("memorySearch ip {} parse is error", ip, e);
			return null;
		}
	}

	/**
	 * 读取 ipInfo 中的信息
	 * @param ip ip
	 * @param function Function
	 * @return 地址
	 */
	public static String getInfo(String ip, Function<IpInfo, String> function) {
		return IpInfo.readInfo(memorySearch(ip), function);
	}

}
