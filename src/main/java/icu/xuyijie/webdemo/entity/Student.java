package icu.xuyijie.webdemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.fastjson2.annotation.JSONField;

import java.util.Date;

/**
 * @author 徐一杰
 * @date 2024/9/23 11:55
 * @description 学生实体类
 */
@ColumnWidth(12)
public class Student {
    private Integer id;

    @ExcelProperty("姓名")
    private String name;

    @ColumnWidth(50)
    @ExcelProperty("图片URL")
    @JSONField(name = "img_url")
    private String imgUrl;

    @ColumnWidth(15)
    @ExcelProperty("学号")
    @JSONField(name = "stu_id")
    private String studentId;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("班级")
    @JSONField(name = "class")
    private String stuClass;

    @ExcelProperty("班主任")
    private Integer teacher;

    public Integer getTeacher() {
        return teacher;
    }

    public void setTeacher(Integer teacher) {
        this.teacher = teacher;
    }

    @ColumnWidth(20)
    @ExcelProperty("入学时间")
    @JSONField(name = "create_time")
    private Date createTime;

    @ExcelProperty("是否毕业")
    @JSONField(name = "is_graduate")
    private Integer isGraduate;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

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
        return "学生{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", studentId='" + studentId + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", stuClass='" + stuClass + '\'' +
                ", createTime=" + createTime +
                ", isGraduate=" + isGraduate +
                '}';
    }
}
