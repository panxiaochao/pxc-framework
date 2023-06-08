package io.github.panxiaochao.operate.log.config;

import io.github.panxiaochao.operate.log.properties.OperateLogProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@code OperateLogAutoConfiguration}
 * <p> description: OperateLog AutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-08
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OperateLogProperties.class)
@ConditionalOnWebApplication
public class OperateLogAutoConfiguration {
}
