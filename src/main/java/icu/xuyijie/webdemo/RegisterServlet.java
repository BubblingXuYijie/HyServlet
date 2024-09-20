package icu.xuyijie.webdemo;

import icu.xuyijie.webdemo.utils.BaseViewServlet;
import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 徐一杰
 * @date 2024/9/14 15:37
 * @description 注册模块
 */
@WebServlet("/register")
public class RegisterServlet extends BaseViewServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");

        // 检查用户是否已注册
        String sql1 = "SELECT * FROM user WHERE username = '" + username + "'";
        List<Map<String, Object>> maps = JdbcUtils.executeQuery(sql1);
        if (!maps.isEmpty()) {
            req.setAttribute("msg", "用户已注册，请直接登录");
            super.processTemplate("login", req, resp);
            return;
        }

        // 校验用户两次密码是否相同，可能 password 或 rePassword 为 null，直接使用 password.equals() 可能报错，所以用 Objects.equals()
        if (!Objects.equals(password, rePassword)) {
            req.setAttribute("registerMsg", "两次密码输入不相同");
            super.processTemplate("login", req, resp);
            return;
        }

        // CURRENT_TIMESTAMP 是 SQL 的一个函数，会生成当前日期时间
        String sql2 = "INSERT INTO user (username, password, create_time) VALUES ('%s', '%s', CURRENT_TIMESTAMP)";
        // String.format 会把 sql2 里的 %s 按顺序替换成后面的参数，省去了使用+号拼接带来的麻烦
        JdbcUtils.execute(String.format(sql2, username, password));
        req.setAttribute("msg", "注册成功，请登录");
        super.processTemplate("login", req, resp);
    }
}
