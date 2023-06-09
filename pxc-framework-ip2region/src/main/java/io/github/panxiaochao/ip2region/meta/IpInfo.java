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
package io.github.panxiaochao.ip2region.meta;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@code IpInfo}
 * <p> description: IP information
 *
 * @author Lypxc
 * @since 2023-04-24
 */
@Getter
@Setter
@ToString
@Schema(name = "IpInfo对象类", description = "IpInfo对象响应类")
public class IpInfo {
    /**
     * 国家
     */
    @Schema(description = "国家")
    private String country;
    /**
     * 区域
     */
    @Schema(description = "区域")
    private String region;
    /**
     * 省
     */
    @Schema(description = "省")
    private String province;
    /**
     * 城市
     */
    @Schema(description = "城市")
    private String city;
    /**
     * 运营商
     */
    @Schema(description = "运营商")
    private String isp;
    /**
     * ip
     */
    @Schema(description = "ip")
    private String ip;

    /**
     * 拼接完整的地址
     *
     * @return address
     */
    public String getAddress() {
        Set<String> regionSet = new LinkedHashSet<>();
        regionSet.add(country);
        regionSet.add(region);
        regionSet.add(province);
        regionSet.add(city);
        regionSet.removeIf(Objects::isNull);
        return String.join("", regionSet);
    }

    /**
     * 拼接完整的地址
     *
     * @return address
     */
    public String getAddressAndIsp() {
        Set<String> regionSet = new LinkedHashSet<>();
        regionSet.add(country);
        regionSet.add(region);
        regionSet.add(province);
        regionSet.add(city);
        regionSet.add(isp);
        regionSet.removeIf(Objects::isNull);
        return String.join(" ", regionSet);
    }
}
