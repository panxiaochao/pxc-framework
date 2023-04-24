package io.github.panxiaochao.ip2region.utils;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@code Ip2regionUtil}
 * <p> description: ip2region 工具类
 *
 * @author Lypxc
 * @since 2023-04-24
 */
public class Ip2regionUtil {

    /**
     * 从内存加载DB数据
     *
     * @param filePath 路径
     * @return Searcher
     */
    public static Searcher loadSearcherFromFile(String filePath) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(filePath);
        try (InputStream inputStream = resource.getInputStream()) {
            return Searcher.newWithBuffer(StreamUtils.copyToByteArray(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("load ip2region file db is error", e);
        }
    }
}
