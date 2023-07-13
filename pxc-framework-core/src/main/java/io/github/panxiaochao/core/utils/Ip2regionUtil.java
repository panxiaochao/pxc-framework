package io.github.panxiaochao.core.utils;

import io.github.panxiaochao.core.utils.ipregion.Ip2RegionLoader;
import io.github.panxiaochao.core.utils.ipregion.IpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * {@code Ip2regionUtil}
 * <p>
 * Ip2region 工具类
 *
 * @author Lypxc
 * @since 2023-07-10
 */
public class Ip2regionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(Ip2regionUtil.class);

	/**
	 * ip 位置 搜索
	 * @param ip ip
	 * @return 位置
	 */
	public static IpInfo memorySearch(String ip) {
		try {
			// 1.ipv4
			String[] ipV4Part = IpInfo.getIpv4Part(ip);
			if (ipV4Part.length == 4) {
				IpInfo ipInfo = IpInfo.toIpInfo(Ip2RegionLoader.INSTANCE().getSearcher().search(ip));
				ipInfo.setIp(ip);
				return ipInfo;
			}
			else if (!ip.contains(":")) {
				// 2.非 ipv6
				LOGGER.error("invalid ipv6 address {}", ip);
				return null;
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			LOGGER.error("memorySearch ip {} is error", ip, e);
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
