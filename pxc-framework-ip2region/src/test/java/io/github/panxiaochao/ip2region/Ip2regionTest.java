package io.github.panxiaochao.ip2region;

import io.github.panxiaochao.ip2region.meta.IpInfo;
import io.github.panxiaochao.ip2region.utils.Ip2regionUtil;
import io.github.panxiaochao.ip2region.utils.Ipv6SearcherUtil;

import java.io.IOException;

/**
 * {@code Ip2regionTest}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-04-24
 */
public class Ip2regionTest {
    public static void main(String[] args) throws IOException {
        // String ip = "61.130.8.5";
        // Searcher searcher = null;
        // try {
        //     searcher = Ip2regionUtil.defaultSearcherByDb();
        //     for (int i = 0; i < 5; i++) {
        //         long sTime = System.nanoTime();
        //         String region = searcher.search(ip);
        //         IpInfo ipInfo = IpInfoUtil.toIpInfo(region);
        //         System.out.printf("{region: %s, ipInfo: %s, ioCount: %d, took: %d ms}\n", region, ipInfo.toString(), searcher.getIOCount(), (System.nanoTime() - sTime) / 1000000);
        //     }
        // } catch (Exception e) {
        //     throw new RuntimeException(e);
        // } finally {
        //     if (searcher != null) {
        //         searcher.close();
        //     }
        // }

        // Ipv6测试
        String ipv6 = "2409:8929:71f:89a7:e42e:dfff:fee6:7a52";
        Ipv6SearcherUtil ipv6SearcherUtil = Ip2regionUtil.defaultIpv6SearcherByDb();
        IpInfo ipInfo = ipv6SearcherUtil.query(ipv6);
        System.out.printf("{ipInfo: %s}\n", ipInfo.toString());
    }
}
