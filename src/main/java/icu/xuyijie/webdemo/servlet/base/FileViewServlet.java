package icu.xuyijie.webdemo.servlet.base;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 徐一杰
 * @date 2024/9/25 14:14
 * @description 静态资源映射（通过 URL 查看文件）
 */
@WebServlet("/file/*")
public class FileViewServlet extends HttpServlet {
    private static final String FILE_DIRECTORY = "E:/file/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 去掉前面的 '/'
        String fileName = request.getPathInfo().substring(1);
        File file = new File(FILE_DIRECTORY, fileName);

        if (file.exists() && !file.isDirectory()) {
            // 获取文件类型，告诉浏览器该怎么展示
            response.setContentType(getServletContext().getMimeType(file.getName()));
            // 获取文件大小
            response.setContentLength((int) file.length());

            // 输出给 response
            try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
