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

import io.github.panxiaochao.ip2region.meta.IpInfo;

import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * {@code IpInfoUtil}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-24
 */
public class IpInfoUtil {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\|");
    private static final Pattern DOT_PATTERN = Pattern.compile("\\.");
    private static final Pattern T_PATTERN = Pattern.compile("\\t");

    /**
     * 获取 ip v4 part
     *
     * @return 是否 ipv4
     */
    public static String[] getIpV4Part(String ip) {
        return DOT_PATTERN.split(ip);
    }

    /**
     * 将 DataBlock 转化为 IpInfo
     *
     * @param region region
     * @return IpInfo
     */
    public static IpInfo toIpInfo(String region) {
        if (region == null) {
            return null;
        }
        IpInfo ipInfo = new IpInfo();
        String[] splitInfoArr = SPLIT_PATTERN.split(region);
        // 补齐5位
        if (splitInfoArr.length < 5) {
            splitInfoArr = Arrays.copyOf(splitInfoArr, 5);
        }
        ipInfo.setCountry(filterZero(splitInfoArr[0]));
        ipInfo.setRegion(filterZero(splitInfoArr[1]));
        ipInfo.setProvince(filterZero(splitInfoArr[2]));
        ipInfo.setCity(filterZero(splitInfoArr[3]));
        ipInfo.setIsp(filterZero(splitInfoArr[4]));
        return ipInfo;
    }

    /**
     * 将 ipv6 地区转化为 IpInfo
     *
     * @param ipRecord ipRecord
     * @return IpInfo
     */
    public static IpInfo toIpV6Info(String[] ipRecord) {
        IpInfo ipInfo = new IpInfo();
        String info1 = ipRecord[0];
        String[] splitInfoArr = T_PATTERN.split(info1);
        // 补齐5位
        if (splitInfoArr.length < 4) {
            splitInfoArr = Arrays.copyOf(splitInfoArr, 4);
        }
        ipInfo.setCountry(splitInfoArr[0]);
        ipInfo.setProvince(splitInfoArr[1]);
        ipInfo.setCity(splitInfoArr[2]);
        ipInfo.setRegion(splitInfoArr[3]);
        ipInfo.setIsp(ipRecord[1]);
        return ipInfo;
    }

    /**
     * 数据过滤，因为 ip2Region 采用 0 填充的没有数据的字段
     *
     * @param info info
     * @return info
     */
    private static String filterZero(String info) {
        // null 或 0 返回 null
        if (info == null || "0".equals(info)) {
            return null;
        }
        return info;
    }

    /**
     * 读取 IpInfo
     *
     * @param ipInfo   IpInfo
     * @param function Function
     * @return info
     */
    public static String readInfo(IpInfo ipInfo, Function<IpInfo, String> function) {
        if (ipInfo == null) {
            return null;
        }
        return function.apply(ipInfo);
    }
}
