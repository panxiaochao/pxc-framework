package io.github.panxiaochao.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * {@code FrameworkContextAutoConfiguration}
 * <p> 框架容器 自定义皮配置
 * <pre>
 *     1. @EnableAspectJAutoProxy(exposeProxy = true) 暴露该代理对象, AopContext 能够访问
 * </pre>
 *
 * @author Lypxc
 * @since 2023-07-06
 */
@AutoConfiguration
@EnableAspectJAutoProxy(exposeProxy = true)
public class FrameworkContextAutoConfiguration {
}
