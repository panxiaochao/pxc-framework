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
package io.github.panxiaochao.ip2region.utils;

import io.github.panxiaochao.ip2region.constants.Ip2regionConstant;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@code Ip2regionUtil}
 * <p> description: ip2region 工具类
 *
 * @author Lypxc
 * @since 2023-04-24
 */
public class Ip2regionUtil {

    /**
     * 从内存加载DB数据
     *
     * @param filePath 路径
     * @return byte[]
     */
    public static byte[] loadByteFromFile(String filePath) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(filePath);
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("load ip2region file db is error", e);
        }
    }

    /**
     * 静态默认获取本地数据库
     *
     * @return Searcher
     */
    public static Searcher defaultSearcherByDb() {
        try {
            byte[] ip2regionBytes = Ip2regionUtil.loadByteFromFile(Ip2regionConstant.IP2REGION_DB_FILE_LOCATION);
            return Searcher.newWithBuffer(ip2regionBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 静态默认获取本地 Ipv6 数据库
     *
     * @return Searcher
     */
    public static Ipv6SearcherUtil defaultIpv6SearcherByDb() {
        byte[] ipv6Bytes = Ip2regionUtil.loadByteFromFile(Ip2regionConstant.IPV6WRY_DB_FILE_LOCATION);
        return Ipv6SearcherUtil.newWithBuffer(ipv6Bytes);
    }
}
