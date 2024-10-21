package icu.xuyijie.webdemo.servlet.teacher;

import icu.xuyijie.webdemo.entity.Teacher;
import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author 徐一杰
 * @date 2024/9/23 11:31
 * @description 点击编辑或新增按钮触发，跳转到编辑/新增页面
 */
@WebServlet("/goEditTeacher")
public class TeacherEditServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Teacher teacher = new Teacher();
        String title = "新增";

        String idString = req.getParameter("id");
        // id 为空说明是编辑按钮跳过来的
        if (idString != null && !idString.isEmpty()) {
            title = "编辑";
            int id = Integer.parseInt(idString);
            String name = req.getParameter("name");
            String sex = req.getParameter("sex");

            teacher.setId(id);
            teacher.setName(name);
            teacher.setSex(sex);
        }

        // 设置页面标题，新增 or 编辑
        req.setAttribute("title", title);
        // 设置学生数据到页面输入框中上，以便页 add.html 面数据回显
        req.setAttribute("teacherData", teacher);

        // 跳转到 add.html 页面
        super.processTemplate("addTeacher", req, resp);
    }
}
