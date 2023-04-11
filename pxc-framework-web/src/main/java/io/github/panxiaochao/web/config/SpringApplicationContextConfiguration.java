package io.github.panxiaochao.web.config;

import io.github.panxiaochao.common.utils.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code SpringApplicationContextConfiguration}
 * <p> description: The Spring ApplicationContext Configuration.
 *
 * @author Lypxc
 * @since 2023-04-11
 */
@Configuration(proxyBeanMethods = false)
public class SpringApplicationContextConfiguration {

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }
}
