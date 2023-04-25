package io.github.panxiaochao.ip2region;

import io.github.panxiaochao.ip2region.meta.IpInfo;
import io.github.panxiaochao.ip2region.utils.Ip2regionUtil;
import io.github.panxiaochao.ip2region.utils.IpInfoUtil;
import org.lionsoul.ip2region.xdb.Searcher;

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
        String ip = "127.0.0.1";
        Searcher searcher = null;
        try {
            searcher = Ip2regionUtil.loadSearcherFromFile("classpath:ip2region/ip2region.xdb");
            for (int i = 0; i < 10; i++) {
                long sTime = System.nanoTime();
                String region = searcher.search(ip);
                IpInfo ipInfo = IpInfoUtil.toIpInfo(region);
                System.out.printf("{region: %s, ipInfo: %s, ioCount: %d, took: %d ms}\n", region, ipInfo.toString(), searcher.getIOCount(), (System.nanoTime() - sTime) / 1000000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (searcher != null) {
                searcher.close();
            }
        }
    }
}
