package icu.xuyijie.webdemo.servlet.teacher;

import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author 徐一杰
 * @date 2024/9/23 11:46
 * @description
 */
@WebServlet("/saveTeacher")
public class TeacherSaveServlet extends BaseViewServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");

        String idString = req.getParameter("id");
        // 如果不为 null，说明是编辑操作
        if (idString != null && !idString.isEmpty()) {
            int id = Integer.parseInt(idString);
            String sql = "UPDATE teacher SET name = ?, sex = ? WHERE id = ?";
            JdbcUtils.execute(sql, name, sex, id);
        } else {
            String sql = "INSERT INTO teacher (name, sex, create_time) VALUES (?, ?, CURRENT_TIMESTAMP)";
            JdbcUtils.execute(sql, name, sex);
        }

        // 保存完以后，重定向到列表页，这里不能使用 super.processTemplate(index, req, resp)，因为这个方法不会刷新页面，添加的数据不会显示出来
        resp.sendRedirect("/teacher");
    }
}
