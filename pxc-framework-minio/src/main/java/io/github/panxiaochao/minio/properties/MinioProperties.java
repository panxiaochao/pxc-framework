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
package io.github.panxiaochao.minio.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code MinioProperties}
 * <p> description: Minio Properties
 *
 * @author Lypxc
 * @since 2023-05-11
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MinioProperties.MINIO_PREFIX, ignoreInvalidFields = true)
public class MinioProperties {

    /**
     * 属性前缀
     */
    public static final String MINIO_PREFIX = "minio";

    /**
     * 是否开启
     */
    private boolean enabled;

    /**
     * Minio 端点地址，不包含端口
     */
    private String endpoint;

    /**
     * 端口
     */
    private int port;

    /**
     * 秘钥
     */
    private String accessKey;

    /**
     * 秘匙
     */
    private String secretKey;

    /**
     * schema 是否是 https
     */
    private boolean secure = false;

    /**
     * 桶名
     */
    private String bucket;

    /**
     * 存储分段大小，默认字节
     */
    private long partSize = 10 * 1024 * 1024;
}
