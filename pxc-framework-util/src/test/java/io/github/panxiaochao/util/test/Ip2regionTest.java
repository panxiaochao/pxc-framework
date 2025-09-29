package io.github.panxiaochao.util.test;

import io.github.panxiaochao.core.utils.Ip2regionUtil;
import io.github.panxiaochao.core.utils.ipregion.IpInfo;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * </p>
 *
 * @author Lypxc
 * @since 2024-04-22
 * @version 1.0
 */
public class Ip2regionTest {

	@Test
	void getRegion() {
		System.out.println(Ip2regionUtil.memorySearch("60.191.8.98"));
		System.out.println(Ip2regionUtil.memorySearch("220.248.12.158"));
		System.out.println(Ip2regionUtil.memorySearch("222.240.36.135"));
		System.out.println(Ip2regionUtil.memorySearch("172.30.13.97"));
		System.out.println(Ip2regionUtil.memorySearch("223.26.64.0"));
		System.out.println(Ip2regionUtil.memorySearch("223.26.128.0"));
		System.out.println(Ip2regionUtil.memorySearch("223.26.67.0"));
		System.out.println(Ip2regionUtil.memorySearch("223.29.220.0"));
		System.out.println(Ip2regionUtil.memorySearch("82.120.124.0"));
		System.out.println(Ip2regionUtil.getInfo("220.248.12.158", IpInfo::getAddress));
		System.out.println(Ip2regionUtil.getInfo("220.248.12.158", IpInfo::getRegion));
	}

	@Test
	void getRegionV6() {
		System.out.println(Ip2regionUtil.memorySearch("240e:57f:32ff:ffff:ffff:ffff:ffff:ffff"));
		System.out.println(Ip2regionUtil.memorySearch("::ffff:1111:2222"));
		System.out.println(Ip2regionUtil.memorySearch("2001:db8::ffff:1111:2222"));
		System.out.println(Ip2regionUtil.memorySearch("::1"));
		System.out.println(Ip2regionUtil.memorySearch("2406:840:20::1"));
		System.out.println(Ip2regionUtil.memorySearch("2c0f:feb0:a::"));
		System.out.println(Ip2regionUtil.memorySearch("240e:109:8047::"));
		System.out.println(Ip2regionUtil.memorySearch("1111:1111:1111::1111"));
	}

}
