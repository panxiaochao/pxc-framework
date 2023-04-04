package io.github.panxiaochao.file.storage.properties;

import io.github.panxiaochao.file.storage.properties.nest.StorageLocal;
import io.github.panxiaochao.file.storage.properties.nest.StorageSftp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * {@code FileStorageProperties}
 * <p> description: 文件存储属性
 *
 * @author Lypxc
 * @since 2023-03-08
 */
@Getter
@Setter
@ConfigurationProperties(prefix = FileStorageProperties.FILE_STORAGE_PREFIX, ignoreInvalidFields = true)
public class FileStorageProperties {

    /**
     * 属性前缀
     */
    public static final String FILE_STORAGE_PREFIX = "file-storage";

    /**
     * 是否开启
     */
    public static final String FILE_STORAGE_ENABLE = FILE_STORAGE_PREFIX + ".enabled";

    /**
     * （必填）存储路径
     */
    private String storagePath;

    /**
     * 存储方方式
     * <p> local(默认), sftp, sftp, oss(暂不支持), minio(暂不支持)
     */
    private String storageType = "local";

    /**
     * （可选）模块名
     */
    private String module;

    /**
     * （可选）文件后缀限制，多个后缀使用英文逗号隔开：.jpg,.png,.gif
     */
    private String limitFileSuffix;

    /**
     * 本地存储
     */
    @NestedConfigurationProperty
    private StorageLocal local = new StorageLocal();

    /**
     * SFTP 存储，默认Linux
     */
    @NestedConfigurationProperty
    private StorageSftp sftp = new StorageSftp();
}
