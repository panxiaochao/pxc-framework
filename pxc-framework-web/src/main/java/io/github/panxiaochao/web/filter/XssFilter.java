/*
 * Copyright © 2022-2024 Lypxc (545685602@qq.com)
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
package io.github.panxiaochao.web.filter;

import io.github.panxiaochao.core.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Xss 过滤器
 * </p>
 *
 * @author Lypxc
 * @since 2024-07-03
 * @version 1.0
 */
public class XssFilter extends OncePerRequestFilter {

	/**
	 * LOGGER XssFilter.class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(XssFilter.class);

	private final static PathMatcher pathMatcher = new AntPathMatcher();

	private final List<String> whiteList;

	public XssFilter(List<String> whiteList) {
		this.whiteList = whiteList;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOGGER.info("XssFilter request url: {}", request.getRequestURI());
		filterChain.doFilter(new XssWrapper(request), response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		// 如果是json数据，则不处理
		String contentType = request.getContentType();
		if (StrUtil.isBlank(contentType)
				|| StrUtil.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE)) {
			return true;
		}
		// 放行不过滤的URL
		return whiteList.stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, request.getRequestURI()));
	}

}
