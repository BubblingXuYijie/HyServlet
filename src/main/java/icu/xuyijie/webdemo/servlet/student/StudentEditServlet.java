package icu.xuyijie.webdemo.servlet.student;

import icu.xuyijie.webdemo.entity.Student;
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
 * @date 2024/9/23 11:31
 * @description 点击编辑或新增按钮触发，跳转到编辑/新增页面
 */
@WebServlet("/goEditStudent")
public class StudentEditServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();
        String title = "新增";

        String idString = req.getParameter("id");
        // id 为空说明是编辑按钮跳过来的
        if (idString != null && !idString.isEmpty()) {
            title = "编辑";
            int id = Integer.parseInt(idString);
            String name = req.getParameter("name");
            String stuId = req.getParameter("stuId");
            String sex = req.getParameter("sex");
            String stuClass = req.getParameter("class");

            // 因为用户可能不输入这个输入框，传来的值就可能为 null 或者 "" 空字符串，这样 Integer.parseInt 就会报错，所以要判空
            int age = 0;
            String ageString = req.getParameter("age");
            if (ageString != null && !ageString.isEmpty()) {
                age = Integer.parseInt(ageString);
            }

            String isGraduateString = req.getParameter("isGraduate");
            int isGraduate = 0;
            if (isGraduateString != null && !isGraduateString.isEmpty()) {
                isGraduate = Integer.parseInt(isGraduateString);
            }

            student.setId(id);
            student.setName(name);
            student.setStudentId(stuId);
            student.setAge(age);
            student.setSex(sex);
            student.setStuClass(stuClass);
            student.setIsGraduate(isGraduate);
        }

        // 设置页面标题，新增 or 编辑
        req.setAttribute("title", title);
        // 设置学生数据到页面输入框中上，以便页 add.html 面数据回显
        req.setAttribute("student", student);

        // 查询教师数据，塞给页面
        String queryTeacherSql = "SELECT * FROM teacher";
        List<Map<String, Object>> teacherList = JdbcUtils.executeQuery(queryTeacherSql);
        req.setAttribute("teacherList", teacherList);

        // 跳转到 add.html 页面
        super.processTemplate("add", req, resp);
    }
}
