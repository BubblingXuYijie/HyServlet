package icu.xuyijie.webdemo;

import icu.xuyijie.webdemo.utils.BaseViewServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author 徐一杰
 * @date 2024/9/19 9:34
 * @description 使用 thymeleaf 自行处理 localhost:8080 打开的首页
 */
@WebServlet("/")
public class IndexServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.processTemplate("login", req, resp);
    }
}
