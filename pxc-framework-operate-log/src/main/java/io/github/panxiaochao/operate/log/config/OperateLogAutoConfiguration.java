package io.github.panxiaochao.operate.log.config;

import io.github.panxiaochao.core.exception.ServerRuntimeException;
import io.github.panxiaochao.core.utils.SpringContextUtil;
import io.github.panxiaochao.operate.log.core.OperateLogDao;
import io.github.panxiaochao.operate.log.core.aspect.OperateLogAspect;
import io.github.panxiaochao.operate.log.core.enums.OperateLogErrorEnum;
import io.github.panxiaochao.operate.log.core.enums.OperateLogType;
import io.github.panxiaochao.operate.log.core.handler.AbstractOperateLogHandler;
import io.github.panxiaochao.operate.log.properties.OperateLogProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

/**
 * {@code OperateLogAutoConfiguration}
 * <p> OperateLog 自动配置类
 *
 * @author Lypxc
 * @since 2023-07-03
 */
@AutoConfiguration
@EnableConfigurationProperties(OperateLogProperties.class)
public class OperateLogAutoConfiguration {
    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogDao operateLogDao(OperateLogProperties operateLogProperties) {
        if (operateLogProperties.logType.equals(OperateLogType.OTHER)) {
            if (!Objects.isNull(operateLogProperties.getHandler())) {
                AbstractOperateLogHandler handler = SpringContextUtil.getBean(operateLogProperties.getHandler());
                Objects.requireNonNull(handler, "请在自定义处理器[" + operateLogProperties.getHandler().getSimpleName() + "]类中加入@Component注解");
                return new OperateLogDao(handler);
            } else {
                throw new ServerRuntimeException(OperateLogErrorEnum.OPERATE_LOG_ERROR);
            }
        }
        return null;
    }
}
