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
package io.github.panxiaochao.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * <p> 获取Spring容器工具类
 *
 * @author Lypxc
 * @since 2022-05-05
 */
public class SpringContextUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final SpringContextUtil INSTANCE = new SpringContextUtil();

    private ApplicationContext applicationContext;

    private SpringContextUtil() {
    }

    /**
     * get SpringContextUtil.
     *
     * @return SpringContextUtil instance
     */
    public static SpringContextUtil getInstance() {
        return INSTANCE;
    }

    /**
     * get ApplicationContext instance
     *
     * @return ApplicationContext
     */
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     * @param beanName bean name
     * @param <T>      type
     * @return bean class
     */
    @SuppressWarnings("all")
    public <T> T getBean(String beanName) {
        return (T) this.applicationContext.getBean(beanName);
    }

    /**
     * @param type type
     * @param <T>  class
     * @return bean class
     */
    public <T> T getBean(Class<T> type) {
        return this.applicationContext.getBean(type);
    }

    /**
     * Get bean by class name.
     *
     * @param className the class name
     * @param <T>       type
     * @return bean by class name
     */
    @SuppressWarnings("all")
    public <T> T getBeanByClassName(final String className) {
        String beanName = this.getBeanName(className);
        try {
            return this.getBean(beanName);
        } catch (BeansException e) {
            return null;
        }
    }

    /**
     * Exist spring bean boolean.
     *
     * @param beanName bean name
     * @return the boolean
     */
    public boolean existBean(final String beanName) {
        return this.applicationContext.containsBean(beanName);
    }

    /**
     * Exist spring bean boolean.
     *
     * @param className class name
     * @return the boolean
     */
    public boolean existBeanByClassName(final String className) {
        String beanName = this.getBeanName(className);
        return this.applicationContext.containsBean(beanName);
    }

    /**
     * get bean name
     *
     * @param className class name
     * @return class name
     */
    public String getBeanName(final String className) {
        String name = className.substring(className.lastIndexOf(".") + 1);
        String start = name.substring(0, 1);
        String end = name.substring(1);
        return start.toLowerCase() + end;
    }

    /**
     * Register bean.
     *
     * @param beanDefinition the bean definition
     * @param classLoader    the class loader
     * @return the beanName
     */
    public String registerBeanDefinition(final GenericBeanDefinition beanDefinition, final ClassLoader classLoader) {
        String beanClassName = beanDefinition.getBeanClassName();
        if (StringUtils.isBlank(beanClassName)) {
            throw new NullPointerException("beanDefinition.beanClassName is null");
        }
        String beanName = getBeanName(beanClassName);
        // 创建Bean工厂
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        beanFactory.setBeanClassLoader(classLoader);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        return beanName;
    }

    /**
     * find properties by key
     *
     * @param key key
     * @return properties
     */
    public String getProperty(String key) {
        return this.applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * get application name
     *
     * @return application name
     */
    public String getApplicationName() {
        return this.getProperty("spring.application.name");
    }

    /**
     * get Active profile
     *
     * @return the array of active profiles
     */
    public String[] getActiveProfiles() {
        return this.applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * get current Active profile
     *
     * @return the current active profile
     */
    public String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles != null && activeProfiles.length > 0 ? activeProfiles[0] : null;
    }


    public void setApplicationContext(@NonNull final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        LOGGER.info(">>> ApplicationContext init success");
    }

    /**
     * 获取aop代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * 发布事件
     *
     * @param event 待发布的事件，事件必须是{@link ApplicationEvent}的子类
     */
    public void publishEvent(ApplicationEvent event) {
        if (null != applicationContext) {
            applicationContext.publishEvent(event);
        }
    }

    /**
     * 发布事件
     * Spring 4.2+ 版本事件可以不再是{@link ApplicationEvent}的子类
     *
     * @param event 待发布的事件
     */
    public void publishEvent(Object event) {
        if (null != applicationContext) {
            applicationContext.publishEvent(event);
        }
    }
}
