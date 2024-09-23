package icu.xuyijie.webdemo.entity;

import java.util.Date;

/**
 * @author 徐一杰
 * @date 2024/9/23 11:55
 * @description 学生实体类
 */
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private String stuClass;
    private Date createTime;
    private Integer isGraduate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsGraduate() {
        return isGraduate;
    }

    public void setIsGraduate(Integer isGraduate) {
        this.isGraduate = isGraduate;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", stuClass='" + stuClass + '\'' +
                ", createTime=" + createTime +
                ", isGraduate=" + isGraduate +
                '}';
    }
}
