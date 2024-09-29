package icu.xuyijie.webdemo.servlet.student;

import com.alibaba.excel.EasyExcelFactory;
import icu.xuyijie.webdemo.entity.Student;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 徐一杰
 * @date 2024/9/29 10:49
 * @description
 */
@WebServlet("/outputData")
public class StudentOutputServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "select * from student";
        List<Map<String, Object>> databaseList = JdbcUtils.executeQuery(sql);

        List<Student> studentList = new ArrayList<>();

        for (Map<String, Object> map : databaseList) {
            Student student = new Student();
            student.setId((Integer) map.get("id"));
            student.setName((String) map.get("name"));
            student.setAge((Integer) map.get("age"));
            student.setSex((String) map.get("sex"));
            student.setStudentId((String) map.get("stu_id"));
            student.setIsGraduate((Integer) map.get("is_graduate"));
            student.setStuClass((String) map.get("class"));
            student.setImgUrl((String) map.get("img_url"));
            // 字符串转为 Date
            student.setCreateTime((Date) map.get("creat_time"));
            studentList.add(student);
        }

        EasyExcelFactory
                .write("E:/file/学生导出" + System.currentTimeMillis() + ".xlsx", Student.class)
                .sheet("学生信息1")
                .doWrite(studentList);
    }
}
