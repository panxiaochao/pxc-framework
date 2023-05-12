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
