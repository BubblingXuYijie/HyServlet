package icu.xuyijie.webdemo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author 徐一杰
 * @date 2024/9/10 8:35
 * @description 登录校验过滤器
 */
// 通配符 /* 代表拦截所有请求，每个请求都要走这个过滤器
@WebFilter("/*")
public class LoginFilter implements Filter {
    /**
     * 允许用户未登录就可以访问的路径列表
     */
    private static final List<String> ALLOW_LIST = List.of("/", "/login", "/login.html", "/assert");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestUri = httpServletRequest.getRequestURI();
        System.out.println("进入登录拦截器，uri：" + requestUri);
        // 如果请求的uri在允许列表里面，直接放行，return
        // requestUri：/assert/backgroundImage.jpg，有些需要放行 /assert 开头的请求，所以下面用 startWith
        for (String allowUrl : ALLOW_LIST) {
            if (requestUri.startsWith(allowUrl)) {
                // 放行，允许请求
                chain.doFilter(request, response);
                return;
            }
        }

        // 检查是否登录
        HttpSession session = httpServletRequest.getSession();
        Object isLogin = session.getAttribute("isLogin");

        // 未登录进入if，跳转回登录界面
        if (isLogin == null || !(boolean) isLogin) {
            PrintWriter writer = httpServletResponse.getWriter();
            writer.println("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>请先登录！！！</title>
                        <script>
                            alert("非法操作，请先登录！！！")
                            window.location.href = "login.html"
                        </script>
                    </head>
                    </html>
                    """);
        } else {
            // 放行，允许请求
            chain.doFilter(request, response);
        }
    }
}
