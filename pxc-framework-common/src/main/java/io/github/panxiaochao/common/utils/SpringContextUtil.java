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
package io.github.panxiaochao.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p> 获取Spring容器工具类
 *
 * @author Lypxc
 * @since 2022-05-05
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private final static Logger LOGGER = LoggerFactory.getLogger(SpringContextUtil.class);
    private static ApplicationContext APPLICATIONCONTEXT = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (Objects.isNull(SpringContextUtil.APPLICATIONCONTEXT)) {
            SpringContextUtil.APPLICATIONCONTEXT = applicationContext;
        }
        if (Objects.nonNull(SpringContextUtil.APPLICATIONCONTEXT)) {
            LOGGER.info(">>> ApplicationContext init success");
        }
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return APPLICATIONCONTEXT;
    }

    /**
     * @param name
     * @param <T>
     * @return T
     */
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * @param tClass
     * @param <T>
     * @return T
     */
    public static <T> T getBean(Class<T> tClass) {
        return getApplicationContext().getBean(tClass);
    }

    /**
     *
     */
    private static void checkApplicationContext() {
        if (APPLICATIONCONTEXT == null) {
            throw new IllegalStateException("ApplicationContext 未注入");
        }
    }

    public static String getProperty(String key) {
        if (null == APPLICATIONCONTEXT) {
            return null;
        }
        return APPLICATIONCONTEXT.getEnvironment().getProperty(key);
    }

    public static String getApplicationName() {
        return getProperty("spring.application.name");
    }

    public static String[] getActiveProfiles() {
        if (null == APPLICATIONCONTEXT) {
            return null;
        }
        return APPLICATIONCONTEXT.getEnvironment().getActiveProfiles();
    }

    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles != null && activeProfiles.length > 0 ? activeProfiles[0] : null;
    }


}
