package icu.xuyijie.webdemo.servlet.student;

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
@WebServlet("/saveStudent")
public class StudentSaveServlet extends BaseViewServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        int age = Integer.parseInt(req.getParameter("age"));
        String stuClass = req.getParameter("stuClass");
        int isGraduate = Integer.parseInt(req.getParameter("isGraduate"));

        String sql = "UPDATE student SET name = ?, sex = ?, age = ?, class = ?, is_graduate = ? WHERE id = ?";
        JdbcUtils.execute(sql, name, sex, age, stuClass, isGraduate, id);

        // 修改完以后，跳转到列表页
        resp.sendRedirect("/student");
    }
}
