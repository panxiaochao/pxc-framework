package io.github.panxiaochao.plugin.loader;

import io.github.panxiaochao.plugin.api.IPlugin;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code PluginLoaderResult}
 * <p> description: The type of IPlugin result.
 *
 * @author Lypxc
 * @since 2023-04-10
 */
@Getter
@Setter
public class PluginLoaderResult {
    private IPlugin plugin;
}
