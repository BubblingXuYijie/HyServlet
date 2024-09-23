package icu.xuyijie.webdemo.servlet.student;

import icu.xuyijie.webdemo.entity.Student;
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
@WebServlet("/goEditStudent")
public class StudentEditServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        Integer age = Integer.parseInt(req.getParameter("age"));
        String stuClass = req.getParameter("class");
        int isGraduate = Integer.parseInt(req.getParameter("isGraduate"));

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setSex(sex);
        student.setStuClass(stuClass);
        student.setIsGraduate(isGraduate);

        req.setAttribute("student", student);

        super.processTemplate("add", req, resp);
    }
}
