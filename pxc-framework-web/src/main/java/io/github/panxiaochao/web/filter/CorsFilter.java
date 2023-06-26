package io.github.panxiaochao.web.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * {@code CorsFilter}
 * <p> description: CorsFilter过滤器
 *
 * @author Lypxc
 * @since 2023-06-26
 */
public class CorsFilter implements Filter {

    /**
     * 当前跨域请求最大有效时长，同一个域名不会再进行检查，默认3600
     */
    private static final String MAX_AGE = "3600";

    /**
     * 允许请求的方法
     */
    private static final List<String> ALLOWED_METHODS = Arrays.asList("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 解决跨域的问题
        cors(request, response);
        // 放行
        filterChain.doFilter(request, response);
    }

    private void cors(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", String.join(",", ALLOWED_METHODS));
        response.setHeader("Access-Control-Max-Age", MAX_AGE);
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
