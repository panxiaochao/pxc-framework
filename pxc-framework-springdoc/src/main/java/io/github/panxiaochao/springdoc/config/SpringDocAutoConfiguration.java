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
package io.github.panxiaochao.springdoc.config;

import io.github.panxiaochao.springdoc.info.SpringSecurityScheme;
import io.github.panxiaochao.springdoc.properties.SpringDocProperties;
import io.github.panxiaochao.springdoc.runner.SpringDocPrint;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * {@code SpringDocConfiguration}
 * <p> SpringDoc 配置文件，默认启用
 *
 * @author Lypxc
 * @since 2022-10-23
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SpringDocProperties.class)
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", matchIfMissing = true)
@ConditionalOnWebApplication
public class SpringDocAutoConfiguration {

    private final SpringDocProperties springDocProperties;

    private final Environment environment;

    /**
     * 配置定义
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(buildInfo())
                .components(createComponents())
                .addSecurityItem(createSecurityItem());
    }

    private SecurityRequirement createSecurityItem() {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList(HttpHeaders.AUTHORIZATION);
        final List<SpringSecurityScheme.Config> configs = springDocProperties.getSecurityScheme().getConfigs();
        if (!CollectionUtils.isEmpty(configs)) {
            configs.forEach(s -> securityRequirement.addList(s.getHeaderName()));
        }
        return securityRequirement;
    }

    private Components createComponents() {
        Components components = new Components();
        components.addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                new SecurityScheme()
                        .name(HttpHeaders.AUTHORIZATION)
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description(HttpHeaders.AUTHORIZATION + " 请求头")
        );
        final List<SpringSecurityScheme.Config> configs = springDocProperties.getSecurityScheme().getConfigs();
        if (!CollectionUtils.isEmpty(configs)) {
            configs.forEach(s ->
                    components.addSecuritySchemes(s.getHeaderName(),
                            new SecurityScheme()
                                    .name(s.getHeaderName())
                                    .type(SecurityScheme.Type.APIKEY)
                                    .in(SecurityScheme.In.HEADER)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                                    .description(s.getHeaderName() + " 请求头")
                    ));
        }
        return components;
    }

    /**
     * 启动打印信息
     *
     * @return SpringDocPrint
     */
    @Bean
    public SpringDocPrint springDocPrint() {
        return new SpringDocPrint();
    }

    /**
     * 构造 Info 信息
     *
     * @return Info
     */
    private Info buildInfo() {
        return new Info()
                .title(springDocProperties.getInfo().getTitle().toUpperCase())
                .description(springDocProperties.getInfo().getDescription())
                .termsOfService(springDocProperties.getInfo().getTermsOfService())
                .version(springDocProperties.getInfo().getVersion())
                .contact(new Contact()
                        .name(springDocProperties.getInfo().getContact().getName())
                        .url(springDocProperties.getInfo().getContact().getUrl())
                        .email(springDocProperties.getInfo().getContact().getEmail())
                        .extensions(springDocProperties.getInfo().getContact().getExtensions())
                )
                .license(new License()
                        .name(springDocProperties.getInfo().getLicense().getName())
                        .url(springDocProperties.getInfo().getLicense().getUrl())
                        .extensions(springDocProperties.getInfo().getLicense().getExtensions())
                );
    }

    /**
     * 分组显示
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group(getApplicationName().toUpperCase())
                .pathsToMatch("/**")
                .build();
    }

    public String getApplicationName() {
        return StringUtils.hasText(environment.getProperty("spring.application.name")) ?
                environment.getProperty("spring.application.name") :
                "DEFAULT GROUP";
    }
}
