package icu.xuyijie.webdemo.servlet.student;

import com.alibaba.excel.EasyExcelFactory;
import icu.xuyijie.webdemo.entity.Student;
import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class StudentOutputServlet extends BaseViewServlet {
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

            // LocalDateTime 转为 Date，相比于 Date，LocalDataTime 缺少时区
            LocalDateTime creatTime = (LocalDateTime) map.get("create_time");
            // 所以 LocalDateTime 转换成 Date 要用下面方式指定时区
            Date date = Date.from(creatTime.atZone(ZoneId.systemDefault()).toInstant());
            student.setCreateTime(date);

            studentList.add(student);
        }

        // 告诉浏览器，我要给你什么格式的数据
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("学生数据excel", StandardCharsets.UTF_8);
        // 设置下载的文件名
        resp.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcelFactory
                .write(resp.getOutputStream(), Student.class)
                .sheet("学生信息1")
                .doWrite(studentList);

        super.processTemplate("index", req, resp);
    }
}
