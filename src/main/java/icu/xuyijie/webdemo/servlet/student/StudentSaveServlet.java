package icu.xuyijie.webdemo.servlet.student;

import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * @author 徐一杰
 * @date 2024/9/23 11:46
 * @description
 */
@WebServlet("/saveStudent")
public class StudentSaveServlet extends BaseViewServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String stuId = req.getParameter("stu111");
        String sex = req.getParameter("sex");
        String teacher = req.getParameter("teacher");
        // Optional 用于替换 if else
        // String ageString = Optional.ofNullable(req.getParameter("age")).orElse("0"), 等效于：
        // if (req.getParameter("age") == null) {
        //      ageString = "0";
        // } else {
        //      ageString = req.getParameter("age");
        // }
        int age = Integer.parseInt(Optional.ofNullable(req.getParameter("age")).orElse("0"));
        String stuClass = req.getParameter("stuClass");
        int isGraduate = Integer.parseInt(Optional.ofNullable(req.getParameter("isGraduate")).orElse("0"));

        String idString = req.getParameter("id");
        // 如果不为 null，说明是编辑操作
        if (idString != null && !idString.isEmpty()) {
            int id = Integer.parseInt(idString);
            String sql = "UPDATE student SET name = ?, stu_id = ?, sex = ?, age = ?, class = ?, is_graduate = ?, teacher = ? WHERE id = ?";
            JdbcUtils.execute(sql, name, stuId, sex, age, stuClass, isGraduate, teacher, id);
        } else {
            String sql = "INSERT INTO student (name, stu_id, sex, age, class, teacher, is_graduate, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            JdbcUtils.execute(sql, name, stuId, sex, age, stuClass, teacher, isGraduate);
        }

        // 保存完以后，重定向到列表页，这里不能使用 super.processTemplate(index, req, resp)，因为这个方法不会刷新页面，添加的数据不会显示出来
        resp.sendRedirect("/student");
    }
}
