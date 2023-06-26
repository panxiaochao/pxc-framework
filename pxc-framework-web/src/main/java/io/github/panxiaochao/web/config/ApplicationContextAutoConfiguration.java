package io.github.panxiaochao.web.config;

import io.github.panxiaochao.core.utils.SpringContextUtil;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;

/**
 * {@code ApplicationContextAutoConfiguration}
 * <p> description: ApplicationContext 自动装配
 *
 * @author Lypxc
 * @since 2023-06-26
 */
@AutoConfiguration
public class ApplicationContextAutoConfiguration {

    /**
     * Config Bean ApplicationContextAware
     *
     * @return the application context aware
     */
    @Bean
    public ApplicationContextAware applicationContextAware() {
        return new SpringApplicationContextAware();
    }

    public static class SpringApplicationContextAware implements ApplicationContextAware {
        @Override
        public void setApplicationContext(@NonNull final ApplicationContext applicationContext) throws BeansException {
            SpringContextUtil.getInstance().setApplicationContext(applicationContext);
        }
    }
}
