package icu.xuyijie.webdemo.servlet.demo;

import icu.xuyijie.webdemo.entity.Student;

/**
 * @author 徐一杰
 * @date 2024/9/30 16:35
 * @description
 */
public class GenericsDemo<T> {
    public String getType(T param) {
        return param.getClass().getName();
    }

    public static void main(String[] args) {
        GenericsDemo<Student> demo = new GenericsDemo<>();
        System.out.println(demo.getType(new Student()));
    }
}
