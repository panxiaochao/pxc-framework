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
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

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

    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    public IpInfo memorySearch(String ip) {
        try {
            IpInfo ipInfo = IpInfoUtil.toIpInfo(searcher.search(ip));
            ipInfo.setIp(ip);
            return ipInfo;
        } catch (Exception e) {
            logger.error("memorySearch ip {} is error", ip, e);
        }
        return null;
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
    public void afterPropertiesSet() {
        this.searcher = Ip2regionUtil.loadSearcherFromFile(Ip2regionConstant.IP2REGION_DB_FILE_LOCATION);
    }

    @Override
    public void destroy() throws Exception {
        if (this.searcher != null) {
            this.searcher.close();
        }
    }
}
