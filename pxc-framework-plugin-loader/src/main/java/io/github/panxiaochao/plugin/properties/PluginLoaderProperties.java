package io.github.panxiaochao.plugin.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code PluginLoaderProperties}
 * <p> description: 属性文件
 *
 * @author Lypxc
 * @since 2023-04-11
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = PluginLoaderProperties.PLUGIN_EXT_PREFIX, ignoreInvalidFields = true)
@ConditionalOnWebApplication
public class PluginLoaderProperties {

    /**
     * 属性前缀
     */
    public static final String PLUGIN_EXT_PREFIX = "plugin-ext";

    /**
     * 是否开启
     */
    private boolean enable;

    /**
     * 插件存放路径
     */
    private String path;

    /**
     * 使用线程数
     */
    private Integer threads = 1;

    /**
     * 定时周期, 单位秒
     */
    private Integer scheduleTime = 300;

    /**
     * 延迟启动时间, 单位秒
     */
    private Integer scheduleDelay = 30;
}
