package icu.xuyijie.webdemo.servlet.student;

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
 * @date 2024/9/20 13:50
 * @description 学生模块
 */
@WebServlet("/student")
public class StudentServlet extends BaseViewServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchString = req.getParameter("searchString");

        List<Map<String, Object>> dataList;
        // 如果搜索框内容为 null 或者 "" 空字符串，查询全部数据
        if (searchString == null || searchString.isEmpty()) {
            String sql = "SELECT s.*,t.name as teacherName FROM `student` s LEFT JOIN teacher t ON t.id = s.teacher;";
            dataList = JdbcUtils.executeQuery(sql);
        } else {
            // 否则模糊匹配字段
            String sql = "SELECT s.*,t.name as teacherName FROM `student` s LEFT JOIN teacher t ON t.id = s.teacher WHERE s.name LIKE ? or s.id = ? or s.age = ? or s.class LIKE ? or s.sex = ? or t.name LIKE ?";
            dataList = JdbcUtils.executeQuery(sql, "%" + searchString + "%", searchString, searchString, "%" + searchString + "%", searchString, "%" + searchString + "%");
        }
        // 把查询出来的学生列表，放进去，以便在页面展示
        req.setAttribute("dataList", dataList);
        // 使用 thymeleaf 输出 index.html 页面
        super.processTemplate("index", req, resp);
    }
}
