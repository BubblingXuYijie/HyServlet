package icu.xuyijie.webdemo.servlet.demo;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.TypeReference;
import icu.xuyijie.webdemo.entity.Student;

import java.util.List;

/**
 * @author 徐一杰
 * @date 2024/9/30 16:12
 * @description json互转示例
 */
public class JsonDemo {
    public static void main(String[] args) {
        // json 字符串 转换为 对象 的过程，叫 序列化，对象转换为 json 字符串 的过程，叫 反序列化
        String jsonString0 = "{" +
                "\"id\":1," +
                "\"name\":\"徐一杰\"" +
                "}";
        // 这是 JDK 15 或以上的语法，三个引号
        String jsonString = """
                {
                    "id": 1,
                    "name": "徐一杰",
                    "img_url": "http://localhost:8080/file/xxx.jpg"
                }
                """;
        Student student = JSON.parseObject(jsonString, Student.class);
        System.out.println("json转为student：" + student);
        System.out.println("student转回json：" + JSON.toJSONString(student));

        // 数组的json
        String arrayJsonString = """
                ["a", "b", "c"]
                """;
        JSONArray jsonArray = JSON.parseArray(arrayJsonString);
        System.out.println(jsonArray);
        System.out.println(jsonArray.get(0));

        List<String> list = JSON.parseArray(arrayJsonString, String.class);
        System.out.println(list);
        System.out.println(list.get(0));

        // 对象 json 数组
        String studentArrayJsonString = """
                [
                    {
                    "id": 1,
                    "name": "徐一杰",
                    "img_url": "http://localhost:8080/file/xxx.jpg"
                    },
                    {
                    "id": 2,
                    "name": "徐一杰2",
                    "img_url": "http://localhost:8080/file/xxx2.jpg"
                    }
                ]
                """;
        List<Student> students = JSON.parseArray(studentArrayJsonString, Student.class);
        System.out.println(students);

        // 传递泛型给 fastjson
        List<Student> list1 = JSON.parseObject(studentArrayJsonString, new TypeReference<List<Student>>(){});
        System.out.println(list1);
        Student student1 = list1.get(0);
        System.out.println(student1);
    }
}
