package icu.xuyijie.webdemo;

import icu.xuyijie.webdemo.utils.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 徐一杰
 * @date 2024/9/20 13:50
 * @description 学生模块
 */
@WebServlet("/student")
public class StudentServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "select * from student";
        List<Map<String, Object>> studentList = JdbcUtils.executeQuery(sql);
        // 把查询出来的学生列表，放进去，以便在页面展示
        req.setAttribute("studentList", studentList);
        // 输出index页面
        super.processTemplate("index", req, resp);
    }
}
