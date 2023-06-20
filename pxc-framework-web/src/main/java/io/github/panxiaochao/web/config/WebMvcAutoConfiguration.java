package io.github.panxiaochao.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * {@code WebMvcAutoConfiguration}
 * <p> description: WebMvcAutoConfiguration
 *
 * @author Lypxc
 * @since 2023-06-20
 */
@AutoConfiguration
@Import({WebMvcConfiguration.class, FilterConfiguration.class, SpringApplicationContextConfiguration.class})
public class WebMvcAutoConfiguration {
}
