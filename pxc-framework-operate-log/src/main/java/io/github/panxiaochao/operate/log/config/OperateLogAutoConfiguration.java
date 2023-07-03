package io.github.panxiaochao.operate.log.config;

import io.github.panxiaochao.operate.log.aspect.OperateLogAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@code OperateLogAutoConfiguration}
 * <p> OperateLog 自动配置类
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@AutoConfiguration
public class OperateLogAutoConfiguration {
    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }
}
