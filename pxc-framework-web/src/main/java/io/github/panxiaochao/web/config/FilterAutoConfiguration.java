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
package io.github.panxiaochao.web.config;

import io.github.panxiaochao.web.filter.CorsFilter;
import io.github.panxiaochao.web.filter.EncodingFilter;
import io.github.panxiaochao.web.filter.RequestWrapperFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * {@code FilterAutoConfiguration}
 * <p>
 * description: Filter过滤器自动装配
 * </p>
 * <p>
 * order的数值越小, 则优先级越高
 * </p>
 *
 * @author Lypxc
 * @since 2023-06-26
 */
@AutoConfiguration
public class FilterAutoConfiguration {

	/**
	 * EncodingFilter 过滤器
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<EncodingFilter> encodingFilter() {
		FilterRegistrationBean<EncodingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new EncodingFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.addServletNames("encodingFilter");
		registrationBean.setOrder(0);
		return registrationBean;
	}

	/**
	 * CorsFilter 过滤器
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new CorsFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.addServletNames("corsFilter");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	/**
	 * RequestWrapperFilter 过滤器
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<RequestWrapperFilter> requestWrapperFilter() {
		FilterRegistrationBean<RequestWrapperFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new RequestWrapperFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.addServletNames("requestWrapperFilter");
		registrationBean.setOrder(2);
		return registrationBean;
	}

}
