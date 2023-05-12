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
package io.github.panxiaochao.minio.config;

import io.github.panxiaochao.common.unit.DataOfSize;
import io.github.panxiaochao.minio.properties.MinioProperties;
import io.github.panxiaochao.minio.template.MinioTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.nio.charset.StandardCharsets;

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

    /**
     * 替换默认 StandardServletMultipartResolver 上传解析器
     * <p>设置大小，单位字节</p>
     *
     * @return CommonsMultipartResolver
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.name());
        commonsMultipartResolver.setMaxUploadSize(DataOfSize.ofMegabytes(50).toBytes());
        // 低于这个大小的文件暂存内存中， 默认是1K
        commonsMultipartResolver.setMaxInMemorySize(20 * 1024 * 1024);
        return commonsMultipartResolver;
    }
}
