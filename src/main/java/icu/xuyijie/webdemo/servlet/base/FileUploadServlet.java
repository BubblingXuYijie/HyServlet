package icu.xuyijie.webdemo.servlet.base;

import icu.xuyijie.webdemo.utils.JdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author 徐一杰
 * @date 2024/9/25 13:19
 * @description 文件上传接口
 */
@WebServlet("/upload")
@MultipartConfig(
        // 写入磁盘的阈值大小，2M
        fileSizeThreshold = 1024 * 1024 * 2,
        // 单次最大能上传的文件大小，10M
        maxFileSize = 1024 * 1024 * 10,
        // 单词请求体大小（请求体大小包括文件大小 + 参数体大小），20M
        maxRequestSize = 1024 * 1024 * 20
)
public class FileUploadServlet extends HttpServlet {
    private static final String FILE_DIRECTORY = "E:/file/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取上传的文件
        Part filePart = req.getPart("file");
        // 获取文件名
        String fileName = filePart.getSubmittedFileName();

        // 获取上传目录文件对象
        File uploadDir = new File(FILE_DIRECTORY);

        if (!uploadDir.exists()) {
            // 如果目录不存在，则创建
            uploadDir.mkdir();
        }

        // 组合保存文件的完整路径
        Path filePath = Paths.get(FILE_DIRECTORY, fileName);
        // 保存文件
        Files.copy(filePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("上传文件：" + filePath);

        // 保存文件访问路径到数据库
        String id = req.getParameter("id");
        String sql = "UPDATE student SET img_url = ? WHERE id = ?";
        JdbcUtils.execute(sql, "http://localhost:8080/file/" + fileName, id);

        // 文件上传成功后重定向或返回成功信息
        resp.sendRedirect("/student");
    }
}
