package icu.xuyijie.webdemo.servlet.student;

import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
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
        String pageNoString = req.getParameter("pageNo");

        // 页码
        int pageNo = 0;
        if (pageNoString != null && !pageNoString.isEmpty()) {
            pageNo = Integer.parseInt(pageNoString);
        }
        // 每页多少条数据
        int pageSize = 5;

        List<Map<String, Object>> dataList;
        // 如果搜索框内容为 null 或者 "" 空字符串，查询全部数据
        if (searchString == null || searchString.isEmpty()) {
            String sql = "SELECT s.*,t.name as teacherName FROM `student` s LEFT JOIN teacher t ON t.id = s.teacher LIMIT ?, 5;";
            dataList = JdbcUtils.executeQuery(sql, pageNo * pageSize);
        } else {
            // 否则模糊匹配字段
            String sql = "SELECT s.*,t.name as teacherName FROM `student` s LEFT JOIN teacher t ON t.id = s.teacher WHERE s.name LIKE ? or s.id = ? or s.age = ? or s.class LIKE ? or s.sex = ? or t.name LIKE ? LIMIT ?, 5";
            dataList = JdbcUtils.executeQuery(sql, "%" + searchString + "%", searchString, searchString, "%" + searchString + "%", searchString, "%" + searchString + "%", pageNo * pageSize);
        }

        // 查询数据库中数据总量
        String countSql = "SELECT COUNT(1) FROM `student`";
        List<Map<String, Object>> mapList = JdbcUtils.executeQuery(countSql);
        Map<String, Object> map = mapList.get(0);
        Object o = map.get("COUNT(1)");
        long count = (Long) o;
        // 计算数据库中数据一共要分为多少页
        long pageTotal = count / pageSize + 1;

        // 页码列表，里面是 1，2，3，4。。。
        List<Long> pageList = new ArrayList<>();
        for (long i = 0; i < pageTotal; i++) {
            pageList.add(i + 1);
        }

        // 把查询出来的学生列表，放进去，以便在页面展示
        req.setAttribute("dataList", dataList);
        req.setAttribute("pageNo", pageNo);
        req.setAttribute("pageTotal", pageTotal);
        req.setAttribute("pageList", pageList);

        // 使用 thymeleaf 输出 index.html 页面
        super.processTemplate("index", req, resp);
    }
}
