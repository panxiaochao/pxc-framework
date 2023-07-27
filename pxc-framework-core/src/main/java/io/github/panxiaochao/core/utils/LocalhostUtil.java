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
package io.github.panxiaochao.core.utils;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * {@code LocalhostUtil}
 * <p>
 * description: Ip获取工具类
 *
 * @author Lypxc
 * @since 2023-01-17
 */
public class LocalhostUtil {

	private final static String UNKNOWN = "unknown";

	/**
	 * IPV4验证
	 */
	private final static Pattern IPV4 = Pattern.compile(
			"\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

	private static String hostIp = "127.0.0.1";

	private static String hostName = UNKNOWN;

	private static String localhostName;

	public static String getHostIp() {
		try {
			if (hostIp.equals(UNKNOWN)) {
				hostIp = getLocalhostStr();
			}
		}
		catch (Exception e) {
		}
		return hostIp;
	}

	public static String getHostName() {
		try {
			if (hostName.equals(UNKNOWN)) {
				hostName = getLocalHostName();
			}
		}
		catch (Exception e) {
		}
		return hostName;
	}

	public static String getLocalhostStr() {
		InetAddress localhost = getLocalhost();
		if (null != localhost) {
			return localhost.getHostAddress();
		}
		return null;
	}

	/**
	 * 根据ip地址计算出long型的数据
	 * @param strIp IP V4 地址
	 * @return long值
	 */
	public static long ipv4ToLong(String strIp) {
		if (isMatch(strIp)) {
			long[] ip = new long[4];
			// 先找到IP地址字符串中.的位置
			int position1 = strIp.indexOf(".");
			int position2 = strIp.indexOf(".", position1 + 1);
			int position3 = strIp.indexOf(".", position2 + 1);
			// 将每个.之间的字符串转换成整型
			ip[0] = Long.parseLong(strIp.substring(0, position1));
			ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
			ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
			ip[3] = Long.parseLong(strIp.substring(position3 + 1));
			return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
		}
		return 0;
	}

	public static BigInteger ipv6ToBigInteger(String ipv6Str) {
		try {
			InetAddress address = InetAddress.getByName(ipv6Str);
			if (address instanceof Inet6Address) {
				return new BigInteger(1, address.getAddress());
			}
		}
		catch (UnknownHostException ignore) {
		}
		return null;
	}

	public static String bigIntegerToIpv6(BigInteger bigInteger) {
		try {
			return InetAddress.getByAddress(bigInteger.toByteArray()).toString().substring(1);
		}
		catch (UnknownHostException ignore) {
			return null;
		}
	}

	public static String getIpByHost(String hostName) {
		try {
			return InetAddress.getByName(hostName).getHostAddress();
		}
		catch (UnknownHostException e) {
			return hostName;
		}
	}

	public static LinkedHashSet<String> localIpv4s() {
		final LinkedHashSet<InetAddress> localAddressList = localAddressList(t -> t instanceof Inet4Address);
		return toIpList(localAddressList);
	}

	public static LinkedHashSet<String> localIpv6s() {
		final LinkedHashSet<InetAddress> localAddressList = localAddressList(t -> t instanceof Inet6Address);
		return toIpList(localAddressList);
	}

	public static LinkedHashSet<String> toIpList(Set<InetAddress> addressList) {
		final LinkedHashSet<String> ipSet = new LinkedHashSet<>();
		for (InetAddress address : addressList) {
			ipSet.add(address.getHostAddress());
		}
		return ipSet;
	}

	public static LinkedHashSet<String> localIps() {
		final LinkedHashSet<InetAddress> localAddressList = localAddressList(null);
		return toIpList(localAddressList);
	}

	/**
	 * 给定内容是否匹配正则
	 * @param content 内容
	 * @return 正则为null或者""则不检查，返回true，内容为null返回false
	 */
	private static boolean isMatch(CharSequence content) {
		if (content == null) {
			// 提供null的字符串为不匹配
			return false;
		}
		return IPV4.matcher(content).matches();
	}

	/**
	 * 获取本机网卡IP地址，规则如下：
	 *
	 * <pre>
	 * 1. 查找所有网卡地址，必须非回路（loopback）地址、非局域网地址（siteLocal）、IPv4地址
	 * 2. 如果无满足要求的地址，调用 {@link InetAddress#getLocalHost()} 获取地址
	 * </pre>
	 * <p>
	 * 此方法不会抛出异常，获取失败将返回<code>null</code><br>
	 * <p>
	 * @return 本机网卡IP地址，获取失败返回<code>null</code>
	 */
	private static InetAddress getLocalhost() {
		final LinkedHashSet<InetAddress> localAddressList = localAddressList(address -> {
			// 非loopback地址，指127.*.*.*的地址
			return false == address.isLoopbackAddress()
					// 需为IPV4地址
					&& address instanceof Inet4Address;
		});
		if (localAddressList != null && localAddressList.size() > 0) {
			InetAddress address2 = null;
			for (InetAddress inetAddress : localAddressList) {
				if (false == inetAddress.isSiteLocalAddress()) {
					// 非地区本地地址，指10.0.0.0 ~ 10.255.255.255、172.16.0.0 ~
					// 172.31.255.255、192.168.0.0 ~ 192.168.255.255
					return inetAddress;
				}
				else if (null == address2) {
					address2 = inetAddress;
				}
			}

			if (null != address2) {
				return address2;
			}
		}

		try {
			return InetAddress.getLocalHost();
		}
		catch (UnknownHostException e) {
			// ignore
		}

		return null;
	}

	public static LinkedHashSet<InetAddress> localAddressList(Predicate<InetAddress> addressFilter) {
		return localAddressList(null, addressFilter);
	}

	private static LinkedHashSet<InetAddress> localAddressList(Predicate<NetworkInterface> networkInterfaceFilter,
			Predicate<InetAddress> addressFilter) {
		Enumeration<NetworkInterface> networkInterfaces;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
		}
		catch (SocketException e) {
			throw new RuntimeException(e);
		}

		if (networkInterfaces == null) {
			throw new RuntimeException("Get network interface error!");
		}

		final LinkedHashSet<InetAddress> ipSet = new LinkedHashSet<>();

		while (networkInterfaces.hasMoreElements()) {
			final NetworkInterface networkInterface = networkInterfaces.nextElement();
			if (networkInterfaceFilter != null && false == networkInterfaceFilter.test(networkInterface)) {
				continue;
			}
			final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
			while (inetAddresses.hasMoreElements()) {
				final InetAddress inetAddress = inetAddresses.nextElement();
				if (inetAddress != null && (null == addressFilter || addressFilter.test(inetAddress))) {
					ipSet.add(inetAddress);
				}
			}
		}

		return ipSet;
	}

	/**
	 * 获得本机MAC地址
	 * @return 本机MAC地址
	 */
	private String getLocalMacAddress() {
		return getMacAddress(getLocalhost());
	}

	/**
	 * 获得指定地址信息中的MAC地址，使用分隔符“-”
	 * @param inetAddress {@link InetAddress}
	 * @return MAC地址，用-分隔
	 */
	private String getMacAddress(InetAddress inetAddress) {
		return getMacAddress(inetAddress, "-");
	}

	/**
	 * 获得指定地址信息中的MAC地址
	 * @param inetAddress {@link InetAddress}
	 * @param separator 分隔符，推荐使用“-”或者“:”
	 * @return MAC地址，用-分隔
	 */
	private String getMacAddress(InetAddress inetAddress, String separator) {
		if (null == inetAddress) {
			return null;
		}
		byte[] mac;
		try {
			mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
		}
		catch (SocketException e) {
			throw new RuntimeException(e);
		}
		if (null != mac) {
			final StringBuilder sb = new StringBuilder();
			String s;
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append(separator);
				}
				// 字节转换为整数
				s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 检查远程端口是否开启
	 * @param address 远程地址
	 * @param timeout 检测超时
	 * @return 远程端口是否开启
	 * @since 5.3.2
	 */
	public static boolean isOpen(InetSocketAddress address, int timeout) {
		try (Socket sc = new Socket()) {
			sc.connect(address, timeout);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	private static String getLocalHostName() {
		if (StringUtils.hasText(localhostName)) {
			return localhostName;
		}
		final InetAddress localhost = getLocalhost();
		if (null != localhost) {
			String name = localhost.getHostName();
			if (!StringUtils.hasText(name)) {
				name = localhost.getHostAddress();
			}
			localhostName = name;
		}
		return localhostName;
	}

}
