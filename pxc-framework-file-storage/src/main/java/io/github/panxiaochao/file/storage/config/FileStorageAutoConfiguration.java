package io.github.panxiaochao.file.storage.config;

import io.github.panxiaochao.file.storage.core.sftp.client.SftpClient;
import io.github.panxiaochao.file.storage.engine.FileStorageEngine;
import io.github.panxiaochao.file.storage.meta.FileMetadata;
import io.github.panxiaochao.file.storage.processor.DelegatingFileStorageProcessor;
import io.github.panxiaochao.file.storage.processor.FileStorageProcessor;
import io.github.panxiaochao.file.storage.processor.LocalStorageProcessor;
import io.github.panxiaochao.file.storage.processor.SftpStorageProcessor;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * {@code FileStorageAuto}
 * <p> description: 文件存储配置类
 *
 * @author Lypxc
 * @since 2023-03-08
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FileStorageProperties.class)
@ConditionalOnProperty(name = FileStorageProperties.FILE_STORAGE_ENABLE, havingValue = "true")
@AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration")
public class FileStorageAutoConfiguration {

    /**
     * 替换默认 StandardServletMultipartResolver 上传解析器
     * <p>设置大小，单位字节</p>
     *
     * @return CommonsMultipartResolver
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(50 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(50 * 1024);
        return commonsMultipartResolver;
    }


    /**
     * 上传附件模版引擎
     *
     * @param fileStorageProperties the file properties
     * @return the file storage engine
     */
    @Bean
    public FileStorageEngine fileStorageEngine(final FileStorageProperties fileStorageProperties) {
        FileStorageProcessor<FileMetadata> fileStorageProcessor = new DelegatingFileStorageProcessor(
                new LocalStorageProcessor(),
                new SftpStorageProcessor()
        );
        return new FileStorageEngine(fileStorageProcessor, fileStorageProperties);
    }

    /**
     * SFTP Configuration
     */
    @Configuration
    @ConditionalOnProperty(prefix = FileStorageProperties.FILE_STORAGE_PREFIX, name = "storageType", havingValue = "sftp")
    static class StorageSftpConfiguration {

        /**
         * 创建Sftp线程池客户端
         *
         * @param fileStorageProperties 配置属性
         * @return SftpClient
         */
        @Bean(destroyMethod = "close")
        public SftpClient sftpClient(final FileStorageProperties fileStorageProperties) {
            return new SftpClient(fileStorageProperties);
        }
    }
}
