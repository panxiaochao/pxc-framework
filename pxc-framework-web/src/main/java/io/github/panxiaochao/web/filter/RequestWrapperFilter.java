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
package io.github.panxiaochao.web.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * RequestWrapper过滤器
 * </p>
 *
 * @author Lypxc
 * @since 2023-06-26
 */
public class RequestWrapperFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String contentType = request.getContentType();
		// 判断请求类型
		if (!StringUtils.hasText(contentType)) {
			filterChain.doFilter(request, response);
		}
		// fix: 请求类型是表单提交的放过
		else if (StringUtils.hasText(contentType) && contentType.contains("multipart/form-data")) {
			filterChain.doFilter(request, response);
		}
		else {
			// 重新包装 Request Wrapper
			HttpServletRequest requestWrapper = new RequestWrapper(request);
			if (null == requestWrapper) {
				filterChain.doFilter(request, response);
			}
			else {
				filterChain.doFilter(requestWrapper, response);
			}
		}
	}

}
