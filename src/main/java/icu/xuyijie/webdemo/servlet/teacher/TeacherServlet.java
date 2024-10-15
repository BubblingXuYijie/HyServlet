package icu.xuyijie.webdemo.servlet.teacher;

import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
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
 * @date 2024/10/15 8:47
 * @description
 */
@WebServlet("/teacher")
public class TeacherServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT * FROM teacher";
        List<Map<String, Object>> teacherList = JdbcUtils.executeQuery(sql);

        req.setAttribute("dataList", teacherList);
        super.processTemplate("teacher", req, resp);
    }
}
