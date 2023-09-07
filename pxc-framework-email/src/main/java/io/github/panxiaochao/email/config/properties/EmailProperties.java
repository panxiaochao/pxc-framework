package io.github.panxiaochao.email.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * Email 属性文件
 * </p>
 *
 * @author Lypxc
 * @since 2023-09-07
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.email", ignoreInvalidFields = true)
public class EmailProperties {

	/**
	 * 过滤开关
	 */
	private Boolean enable;

	/**
	 * SMTP 服务器域名
	 */
	private String host;

	/**
	 * SMTP 服务端口
	 */
	private Integer port;

	/**
	 * 是否需要用户名密码验证
	 */
	private Boolean auth;

	/**
	 * 邮箱登录名
	 */
	private String loginName;

	/**
	 * 邮箱登录密码
	 */
	private String password;

	/**
	 * 发送方，遵循RFC-822标准
	 */
	private String from;

	/**
	 * 使用 STARTTLS安全连接，STARTTLS是对纯文本通信协议的扩展。它将纯文本连接升级为加密连接（TLS或SSL）， 而不是使用一个单独的加密通信端口。
	 */
	private Boolean starttlsEnable;

	/**
	 * 使用 SSL安全连接
	 */
	private Boolean sslEnable;

	/**
	 * SMTP 超时时长，单位毫秒，缺省值不超时
	 */
	private Long timeout;

	/**
	 * Socket 连接超时值，单位毫秒，缺省值不超时
	 */
	private Long connectionTimeout;

}
