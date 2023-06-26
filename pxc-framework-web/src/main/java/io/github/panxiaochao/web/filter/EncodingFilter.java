package io.github.panxiaochao.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {@code EncodingFilter}
 * <p> description: Encoding过滤器
 *
 * @author Lypxc
 * @since 2023-06-26
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 编码
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
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
