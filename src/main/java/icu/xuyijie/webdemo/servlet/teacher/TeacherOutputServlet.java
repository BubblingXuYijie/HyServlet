package icu.xuyijie.webdemo.servlet.teacher;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson2.JSON;
import icu.xuyijie.webdemo.entity.Teacher;
import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 徐一杰
 * @date 2024/9/29 10:49
 * @description
 */
@WebServlet("/outputTeacherData")
public class TeacherOutputServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 查询要导出的教师数据
        String sql = "select * from teacher";
        List<Map<String, Object>> databaseList = JdbcUtils.executeQuery(sql);

        // new 一个 Teacher 类型的数组收集教师数据
        List<Teacher> teacherList = new ArrayList<>();

        // 把数据库查询出来的 Map 类型的数据取出来，放入 Student 对象里，添加到 teacherList
        for (Map<String, Object> map : databaseList) {

            // map 数据格式长这样：{id=1, name=徐一杰}，map 转换成 json
            String jsonString = JSON.toJSONString(map);
            // 把 json 字符串转换为 Teacher 对象
            Teacher teacher = JSON.parseObject(jsonString, Teacher.class);

            teacherList.add(teacher);
        }

        // 告诉浏览器，我要给你什么格式的数据
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("教师数据excel", StandardCharsets.UTF_8);
        // 设置下载的文件名
        resp.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcelFactory
                // resp.getOutputStream 是让浏览器直接下载
                .write(resp.getOutputStream(), Teacher.class)
                .sheet("教师信息1")
                .doWrite(teacherList);

        // 下载链接不跳转，保持在当前的index页面
        super.processTemplate("index", req, resp);
    }
}
