package io.github.panxiaochao.ip2region.config;

import io.github.panxiaochao.ip2region.template.Ip2regionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code Ip2regionAutoConfiguration}
 * <p> description: ip2region auto configuration
 *
 * @author Lypxc
 * @since 2023-04-24
 */
@Configuration(proxyBeanMethods = false)
public class Ip2regionAutoConfiguration {

    /**
     * Ip2region template类
     *
     * @return Ip2regionTemplate
     */
    @Bean
    public Ip2regionTemplate ip2regionTemplate() {
        return new Ip2regionTemplate();
    }
}
