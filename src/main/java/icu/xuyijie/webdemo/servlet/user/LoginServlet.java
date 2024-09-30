package icu.xuyijie.webdemo.servlet.user;

import icu.xuyijie.webdemo.servlet.base.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 徐一杰
 * @date 2024/9/9 11:49
 * @description 登录模块
 */
@WebServlet("/login")
public class LoginServlet extends BaseViewServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取用户填写的用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //String sql = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'"
        // 不要拼接 sql，把参数传入方法中，防止 SQL 注入攻击
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        List<Map<String, Object>> result = JdbcUtils.executeQuery(sql, username, password);
        if (result.isEmpty()) {
            // 登录失败，返回 401 错误
            //resp.sendError(401, "login failed")
            // 也可以用 HttpServletResponse 枚举来代替 401
            //resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "login failed")
            // 设置一个字段 msg，值为 登录失败，以便在页面上展示
            req.setAttribute("msg", "登录失败");
            // 跳转到 login.html 页面，使用 thymeleaf处理
            super.processTemplate("login", req, resp);
        } else {
            // 如果查询到数据，说明用户用户名和密码输入的都正确
            // 每次请求中，都有一个session对象，只要浏览器不关闭，这个session对象就是不变的
            HttpSession session = req.getSession();
            // 登录成功后设置一个登录标识，方便其他页面判断是否登录了
            session.setAttribute("isLogin", true);
            // 重定向到 /student 对应的servlet类，，这里不能使用 super.processTemplate(index, req, resp)，因为这个方法只是跳转页面，而不会走 /student 对应的 servlet 类，数据不会显示出来
            resp.sendRedirect("/student");
        }
    }
}
