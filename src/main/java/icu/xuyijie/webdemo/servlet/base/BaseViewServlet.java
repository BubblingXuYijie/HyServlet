package icu.xuyijie.webdemo.servlet.base;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

/**
 * @author 徐一杰
 * @date 2024/9/18 15:57
 * @description thymeleaf 模板引擎基础类，使用方法是继承这个类
 */
public class BaseViewServlet extends HttpServlet {
    private final transient TemplateEngine templateEngine = new TemplateEngine();
    private transient JakartaServletWebApplication jakartaServletWebApplication;

    /**
     * init 方法会在每个子类的 doGet 或 doPost 方法执行前执行，从而达到初始化 TemplateEngine 的目的
     */
    @Override
    public void init() throws ServletException {
        // 取出 servlet 程序运行环境
        ServletContext servletContext = getServletContext();
        // 根据当前 servlet 程序运行环境，创建 Thymeleaf 处理器
        jakartaServletWebApplication = JakartaServletWebApplication.buildApplication(servletContext);
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(jakartaServletWebApplication);
        // 设置前端页面存放在 webapp 目录下的位置，/ 就是存放在 webapp 根目录下
        templateResolver.setPrefix("/");
        // 设置文件扩展名为 html
        templateResolver.setSuffix(".html");
        // 设置文件类型为 html
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // 设置编码方式，避免中文乱码
        templateResolver.setCharacterEncoding("UTF-8");
        // 把 Thymeleaf 处理器设置到 Thymeleaf 引擎中
        templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     * 子类在 doGet 或 doPost 中调用这个方法，实现 thymeleaf 页面跳转
     * @param templateName html 文件名，不带 .html 后缀
     * @param req 请求信息
     * @param resp 响应信息
     */
    public void processTemplate(String templateName, HttpServletRequest req, HttpServletResponse resp) {
        // 设置要展示的页面为 html，并且指定编码方式为 utf-8
        resp.setContentType("text/html;charset=utf-8");

        // 根据 servlet 程序运行环境创建 IWebExchange
        IWebExchange iWebExchange = jakartaServletWebApplication.buildExchange(req, resp);
        // 初始化 WebContext
        WebContext webContext = new WebContext(iWebExchange, req.getLocale());

        try {
            // 输出页面
            templateEngine.process(templateName, webContext, resp.getWriter());
        } catch (IOException e) {
            System.out.println("输出模板页面异常：" + templateName);
        }
    }

}
