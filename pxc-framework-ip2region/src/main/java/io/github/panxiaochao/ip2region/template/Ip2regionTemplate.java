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
package io.github.panxiaochao.ip2region.template;

import io.github.panxiaochao.ip2region.constants.Ip2regionConstant;
import io.github.panxiaochao.ip2region.meta.IpInfo;
import io.github.panxiaochao.ip2region.utils.Ip2regionUtil;
import io.github.panxiaochao.ip2region.utils.IpInfoUtil;
import io.github.panxiaochao.ip2region.utils.Ipv6SearcherUtil;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.function.Function;

/**
 * {@code Ip2regionTemplate}
 * <p> description: Ip2regionTemplate
 *
 * @author Lypxc
 * @since 2023-04-24
 */
public class Ip2regionTemplate implements InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Searcher searcher;

    private Ipv6SearcherUtil ipv6SearcherUtil;

    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    public IpInfo memorySearch(String ip) {
        try {
            // 1.ipv4
            String[] ipV4Part = IpInfoUtil.getIpv4Part(ip);
            if (ipV4Part.length == 4) {
                IpInfo ipInfo = IpInfoUtil.toIpInfo(searcher.search(ip));
                ipInfo.setIp(ip);
                return ipInfo;
            } else {
                return null;
            }
            // 2.非 ipv6
            // if (!ip.contains(":")) {
            //     logger.error("invalid ipv6 address {}", ip);
            //     return null;
            // }
            // // 3. ipv6
            // return ipv6SearcherUtil.query(ip);
        } catch (Exception e) {
            logger.error("memorySearch ip {} is error", ip, e);
            return null;
        }
    }

    /**
     * 读取 ipInfo 中的信息
     *
     * @param ip       ip
     * @param function Function
     * @return 地址
     */
    public String getInfo(String ip, Function<IpInfo, String> function) {
        return IpInfoUtil.readInfo(memorySearch(ip), function);
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        // load IP2REGION_DB_FILE_LOCATION
        byte[] ip2regionBytes = Ip2regionUtil.loadByteFromFile(Ip2regionConstant.IP2REGION_DB_FILE_LOCATION);
        this.searcher = Searcher.newWithBuffer(ip2regionBytes);
        // load IPV6WRY_DB_FILE_LOCATION
        byte[] ipv6Bytes = Ip2regionUtil.loadByteFromFile(Ip2regionConstant.IPV6WRY_DB_FILE_LOCATION);
        this.ipv6SearcherUtil = Ipv6SearcherUtil.newWithBuffer(ipv6Bytes);
    }

    @Override
    public void destroy() throws Exception {
        if (this.searcher != null) {
            this.searcher.close();
        }
    }
}
