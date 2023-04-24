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

    private static final byte[] SHIFT_INDEX = {24, 16, 8, 0};

    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    public IpInfo memorySearch(long ip) {
        try {
            return IpInfoUtil.toIpInfo(searcher.search(ip));
        } catch (IOException e) {
            logger.error("memorySearch ip {} is error", ip, e);
        }
        return null;
    }

    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    public IpInfo memorySearch(String ip) {
        String[] ipV4Part = IpInfoUtil.getIpV4Part(ip);
        if (ipV4Part.length == 4) {
            return memorySearch(getIpAdder(ipV4Part));
        }
        return null;
    }

    private long getIpAdder(String[] ipParts) {
        long ipAdder = 0;
        for (int i = 0; i < ipParts.length; i++) {
            int val = Integer.parseInt(ipParts[i]);
            if (val > 255) {
                throw new IllegalArgumentException("ip part `" + ipParts[i] + "` should be less then 256");
            }
            ipAdder |= ((long) val << SHIFT_INDEX[i]);
        }
        return ipAdder & 0xFFFFFFFFL;
    }

    /**
     * 读取 ipInfo 中的信息
     *
     * @param ip       ip
     * @param function Function
     * @return 地址
     */
    public String getInfo(long ip, Function<IpInfo, String> function) {
        return IpInfoUtil.readInfo(memorySearch(ip), function);
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

    /**
     * 获取地址信息
     *
     * @param ip ip
     * @return 地址
     */
    public String getAddress(long ip) {
        return getInfo(ip, IpInfo::getAddress);
    }

    /**
     * 获取地址信息
     *
     * @param ip ip
     * @return 地址
     */
    public String getAddress(String ip) {
        return getInfo(ip, IpInfo::getAddress);
    }

    /**
     * 获取地址信息包含 isp
     *
     * @param ip ip
     * @return 地址
     */
    public String getAddressAndIsp(long ip) {
        return getInfo(ip, IpInfo::getAddressAndIsp);
    }

    /**
     * 获取地址信息包含 isp
     *
     * @param ip ip
     * @return 地址
     */
    public String getAddressAndIsp(String ip) {
        return getInfo(ip, IpInfo::getAddressAndIsp);
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
