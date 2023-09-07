package io.github.panxiaochao.email.config;

import cn.hutool.extra.mail.MailAccount;
import io.github.panxiaochao.email.config.properties.EmailProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * Email 自动配置类
 * </p>
 *
 * @author Lypxc
 * @since 2023-09-07
 */
@AutoConfiguration
@EnableConfigurationProperties(EmailProperties.class)
@ConditionalOnProperty(name = "spring.email.enable", havingValue = "true")
public class EmailAutoConfiguration {

	@Bean
	public MailAccount mailAccount(EmailProperties emailProperties) {
		MailAccount account = new MailAccount();
		account.setHost(emailProperties.getHost());
		account.setPort(emailProperties.getPort());
		account.setAuth(emailProperties.getAuth());
		account.setFrom(emailProperties.getFrom());
		account.setUser(emailProperties.getLoginName());
		account.setPass(emailProperties.getPassword());
		account.setSocketFactoryPort(emailProperties.getPort());
		account.setStarttlsEnable(emailProperties.getStarttlsEnable());
		account.setSslEnable(emailProperties.getSslEnable());
		account.setTimeout(emailProperties.getTimeout());
		account.setConnectionTimeout(emailProperties.getConnectionTimeout());
		return account;
	}

}
