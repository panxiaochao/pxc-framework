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
package io.github.panxiaochao.core.utils.ipregion;

import io.github.panxiaochao.core.utils.ResourceUtil;
import lombok.Getter;
import org.lionsoul.ip2region.xdb.LongByteArray;
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * Ip2RegionLoader 资源加载
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-10
 */
@Getter
public class Ip2RegionLoader {

	/**
	 * LOGGER Ip2RegionLoader.class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Ip2RegionLoader.class);

	private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

	/**
	 * ip2region_v4.db 文件路径
	 */
	private static final String IP2REGION_V4_DB_LOCATION = "classpath*:/ip2region/ip2region_v4.xdb";

	/**
	 * ip2region_v6.db 文件路径
	 */
	private static final String IP2REGION_V6_DB_LOCATION = "classpath*:/ip2region/ip2region_v6.xdb";

	private static final Searcher SEARCHER_V4;

	private static final Searcher SEARCHER_V6;

	static {
		try {
			SEARCHER_V4 = Searcher.newWithBuffer(Version.IPv4, loadContentFromFile(IP2REGION_V4_DB_LOCATION));
			LOGGER.info("配置[ip2region_v4]成功！");
			SEARCHER_V6 = Searcher.newWithBuffer(Version.IPv6, loadContentFromFile(IP2REGION_V6_DB_LOCATION));
			LOGGER.info("配置[ip2region_v6]成功！");
		}
		catch (IOException e) {
			throw new RuntimeException("load ip2region_v4 file db is error", e);
		}
	}

	/**
	 * Don't new Constructor
	 */
	private Ip2RegionLoader() {
	}

	/**
	 * 获取 ip2region_v4 对象
	 * @return Searcher
	 */
	public static Searcher searcherV4() {
		return SEARCHER_V4;
	}

	/**
	 * 获取 ip2region_v6 对象
	 * @return Searcher
	 */
	public static Searcher searcherV6() {
		return SEARCHER_V6;
	}

	/**
	 * 从内存加载DB数据
	 * @param filePath 路径
	 * @return byte[]
	 */
	public static LongByteArray loadContentFromFile(String filePath) {
		Resource[] resources = getResources(filePath);
		for (Resource resource : resources) {
			Assert.isTrue(resource.exists(), "Cannot find config location: " + resource
					+ " (please add config file or check your holiday json configuration)");
			try (InputStream inputStream = resource.getInputStream()) {
				byte[] bytes = ResourceUtil.readByteArray(inputStream);
				final LongByteArray byteArray = new LongByteArray();
				byteArray.append(bytes);
				return byteArray;
			}
			catch (IOException e) {
				throw new RuntimeException("load ip2region file db is error", e);
			}
		}
		return null;
	}

	public static Resource[] getResources(String location) {
		try {
			return RESOURCE_PATTERN_RESOLVER.getResources(location);
		}
		catch (IOException e) {
			return new Resource[0];
		}
	}

}
