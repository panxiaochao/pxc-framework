package io.github.panxiaochao.web.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 自定属性配置
 * </p>
 *
 * @author Lypxc
 * @since 2023-07-17
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.pxc-web", ignoreInvalidFields = true)
public class WebProperties {

	/**
	 * 是否开启 Cors
	 */
	private boolean cors;

}
