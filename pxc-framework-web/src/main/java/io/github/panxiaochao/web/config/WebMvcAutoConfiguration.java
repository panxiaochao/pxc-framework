package io.github.panxiaochao.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * {@code WebMvcAutoConfiguration}
 * <p> description: WebMvcAutoConfiguration is a AutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-26
 */
@AutoConfiguration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    /**
     * 设置 StringHttpMessageConverter 编码 UTF-8
     *
     * @param converters the list of configured converters to be extended
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(c -> c instanceof StringHttpMessageConverter)
                .map(c -> (StringHttpMessageConverter) c)
                .forEach(c -> c.setDefaultCharset(StandardCharsets.UTF_8));
    }
}
