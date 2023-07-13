package io.github.panxiaochao.core.utils.ipregion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * {@code IpInfo}
 * <p>
 * IpInfo 地址实体类
 *
 * @author Lypxc
 * @since 2023-07-10
 */
@Getter
@Setter
@ToString
public class IpInfo {

	private static final Pattern SPLIT_PATTERN = Pattern.compile("\\|");

	private static final Pattern DOT_PATTERN = Pattern.compile("\\.");

	private static final Pattern T_PATTERN = Pattern.compile("\\t");

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 区域
	 */
	private String area;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 运营商
	 */
	private String isp;

	/**
	 * ip
	 */
	private String ip;

	/**
	 * 原生数据
	 */
	private String region;

	/**
	 * 拼接完整的地址
	 * @return address
	 */
	public String getAddress() {
		Set<String> regionSet = new LinkedHashSet<>();
		regionSet.add(country);
		regionSet.add(area);
		regionSet.add(province);
		regionSet.add(city);
		regionSet.removeIf(Objects::isNull);
		return String.join("|", regionSet);
	}

	/**
	 * 拼接完整的地址
	 * @return address
	 */
	public String getAddressAndIsp() {
		Set<String> regionSet = new LinkedHashSet<>();
		regionSet.add(country);
		regionSet.add(area);
		regionSet.add(province);
		regionSet.add(city);
		regionSet.add(isp);
		regionSet.removeIf(Objects::isNull);
		return String.join("|", regionSet);
	}

	/**
	 * 获取 ip v4 part
	 * @return 是否 ipv4
	 */
	public static String[] getIpv4Part(String ip) {
		return DOT_PATTERN.split(ip);
	}

	/**
	 * 将 region 转化为 IpInfo
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
		ipInfo.setArea(filterZero(splitInfoArr[1]));
		ipInfo.setProvince(filterZero(splitInfoArr[2]));
		ipInfo.setCity(filterZero(splitInfoArr[3]));
		ipInfo.setIsp(filterZero(splitInfoArr[4]));
		ipInfo.setRegion(region);
		return ipInfo;
	}

	/**
	 * 数据过滤，因为 ip2Region 采用 0 填充的没有数据的字段
	 * @param info info
	 * @return info
	 */
	private static String filterZero(String info) {
		// null 或 0 返回 null
		if (null == info || "0".equals(info)) {
			return null;
		}
		return info;
	}

	/**
	 * 读取 IpInfo
	 * @param ipInfo IpInfo
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
