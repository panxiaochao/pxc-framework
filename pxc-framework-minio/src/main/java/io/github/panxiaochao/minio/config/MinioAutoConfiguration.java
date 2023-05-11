package io.github.panxiaochao.minio.config;

import io.github.panxiaochao.minio.properties.MinioProperties;
import io.github.panxiaochao.minio.template.MinioTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code MinioAutoConfiguration}
 * <p> description: Minio 配置类
 *
 * @author Lypxc
 * @since 2023-05-11
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(name = "minio.enabled", havingValue = "true")
public class MinioAutoConfiguration {

    /**
     * 注册 MinioTemplate
     *
     * @param minioProperties minio properties
     * @return MinioTemplate
     */
    @Bean
    public MinioTemplate minioTemplate(MinioProperties minioProperties) {
        return new MinioTemplate(minioProperties);
    }
}
