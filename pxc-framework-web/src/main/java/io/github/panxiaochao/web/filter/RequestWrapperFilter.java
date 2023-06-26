package io.github.panxiaochao.web.filter;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code RequestWrapperFilter}
 * <p> description: RequestWrapper过滤器
 *
 * @author Lypxc
 * @since 2023-06-26
 */
public class RequestWrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contentType = request.getContentType();
        // 判断请求类型
        if (!StringUtils.hasText(contentType)) {
            filterChain.doFilter(request, response);
        } else if (StringUtils.hasText(contentType) || contentType.contains("multipart/form-data")) {
            filterChain.doFilter(request, response);
        } else {
            // 重新包装 Request Wrapper
            request = new RequestWrapper(request);
            if (null == request) {
                filterChain.doFilter(servletRequest, response);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
