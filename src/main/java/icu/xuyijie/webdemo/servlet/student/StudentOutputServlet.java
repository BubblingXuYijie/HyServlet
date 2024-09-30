package icu.xuyijie.webdemo.servlet.student;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson2.JSON;
import icu.xuyijie.webdemo.entity.Student;
import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
        // 查询要导出的学生数据
        String sql = "select * from student";
        List<Map<String, Object>> databaseList = JdbcUtils.executeQuery(sql);

        // new 一个 Student 类型的数组收集学生数据
        List<Student> studentList = new ArrayList<>();

        // 把数据库查询出来的 Map 类型的数据取出来，放入 Student 对象里，添加到 studentList
        for (Map<String, Object> map : databaseList) {

            // map 数据格式长这样：{id=1, name=徐一杰}，map 转换成 json
            String jsonString = JSON.toJSONString(map);
            // 把 json 字符串转换为 Student 对象
            Student student = JSON.parseObject(jsonString, Student.class);

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
                // resp.getOutputStream 是让浏览器直接下载
                .write(resp.getOutputStream(), Student.class)
                .sheet("学生信息1")
                .doWrite(studentList);

        // 下载链接不跳转，保持在当前的index页面
        super.processTemplate("index", req, resp);
    }
}
