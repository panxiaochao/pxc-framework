package io.github.panxiaochao.springdoc.info;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * {@code SpringSecurityScheme}
 * <p> description: 自定义Auth HeaderName
 *
 * @author Lypxc
 * @since 2023-03-06
 */
@Getter
@Setter
public class SpringSecurityScheme {

    /**
     * 多个headerName
     */
    private List<Config> configs;

    @Getter
    @Setter
    public static class Config {
        /**
         * headerName, 例如：AccessToken
         */
        private String headerName;
    }
}
