package io.github.panxiaochao.minio.utils;

import io.minio.MinioClient;

/**
 * {@code MinioUtil}
 * <p> description: MinioUtil 工具类
 *
 * @author Lypxc
 * @since 2023-05-12
 */
public class MinioUtil {

    /**
     * 创建Minio客户端连接
     *
     * @param endpoint  端点链接，不包含端口
     * @param port      端口
     * @param secure    scheme是否是Https
     * @param accessKey 秘钥
     * @param secretKey 密匙
     * @return MinioClient
     */
    public static MinioClient createMinioClient(String endpoint, int port, boolean secure, String accessKey, String secretKey) {
        return MinioClient.builder()
                .endpoint(endpoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * 创建Minio客户端连接
     *
     * @param endpointUrl 端点链接，包含端口
     * @param accessKey   秘钥
     * @param secretKey   密匙
     * @return MinioClient
     */
    public static MinioClient createMinioClient(String endpointUrl, String accessKey, String secretKey) {
        return MinioClient.builder()
                .endpoint(endpointUrl)
                .credentials(accessKey, secretKey)
                .build();
    }
}
