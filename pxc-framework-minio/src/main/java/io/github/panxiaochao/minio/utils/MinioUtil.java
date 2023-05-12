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
