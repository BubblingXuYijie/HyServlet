package icu.xuyijie.webdemo;

import icu.xuyijie.webdemo.utils.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author 徐一杰
 * @date 2024/9/23 10:33
 * @description
 */
@WebServlet("/delStudent")
public class StudentDeleteServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("stuId");
        String sql = "DELETE FROM student WHERE id = ?";
        JdbcUtils.execute(sql, id);
        // 帮用户刷新一下页面
        resp.sendRedirect("/student");
    }
}
