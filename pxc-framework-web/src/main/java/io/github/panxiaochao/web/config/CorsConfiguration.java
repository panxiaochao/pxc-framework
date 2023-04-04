package io.github.panxiaochao.web.config;

import io.github.panxiaochao.web.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code CorsConfiguration}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-07
 */
@Configuration
public class CorsConfiguration {

    /**
     * Cors Filter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addServletNames("corsFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
