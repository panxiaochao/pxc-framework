/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.plugin.loader;

import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.Optional;

/**
 * {@code PluginPathParser}
 * <p> description: Plugin Path Parser
 *
 * @author Lypxc
 * @since 2023-04-10
 */
public class PluginPathParser {
    private static final String PLUGIN_EXT_PATH = "plugin-ext";

    private static final String DEFAULT_PLUGIN_EXT_PATH = "/plugin-ext/";

    private PluginPathParser() {
    }

    /**
     * Gets plugin file.
     *
     * @param path the path
     * @return the plugin jar file.
     */
    public static File getPluginFile(final String path) {
        String pluginPath = getPluginPath(path);
        return new File(pluginPath);
    }

    /**
     * Gets plugin path.
     *
     * @param path the path
     * @return the plugin path
     */
    public static String getPluginPath(final String path) {
        if (StringUtils.hasText(path)) {
            return path;
        }
        String pluginPath = System.getProperty(PLUGIN_EXT_PATH);
        if (StringUtils.hasText(pluginPath)) {
            return pluginPath;
        }
        URL resource = PluginPathParser.class.getResource(DEFAULT_PLUGIN_EXT_PATH);
        return Optional.ofNullable(resource).map(URL::getPath).orElse(DEFAULT_PLUGIN_EXT_PATH);
    }
}
