package icu.xuyijie.webdemo;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * @author 徐一杰
 * @date 2024/9/9 18:05
 * @description
 */
// @xxx 这个玩意叫注解，/hello-servlet 是请求路径，也就是form表单的action要请求的uri
@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("这是get方法");
        // request 里面存储了 html 发送请求的信息，比如可以拿到参数
        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        // 前端传输的值是列表形式，用 getParameterValues
        String[] hobbies = request.getParameterValues("hobby");
        System.out.println(username);
        System.out.println(gender);
        System.out.println(Arrays.toString(hobbies));


        // 获取用户设备 IP
        String remoteAddr = request.getRemoteAddr();
        // 用户设备名称
        String remoteHost = request.getRemoteHost();
        // 获取URL
        String requestUrl = String.valueOf(request.getRequestURL());
        // 获取URI
        String requestUri = request.getRequestURI();
        System.out.println("用户设备IP：" + remoteAddr);
        System.out.println("用户设备名称：" + remoteHost);
        System.out.println("请求URL：" + requestUrl);
        System.out.println("请求URI：" + requestUri);

        String accept = request.getHeader("accept");
        System.out.println("Accept请求头：" + accept);

        // 打印全部请求头信息
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            System.out.println(request.getHeader(s));
        }
    }
}