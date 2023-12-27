package org.xhliu.springtransaction.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 *@author lxh
 */
@TableName("course_info")
public class CourseInfo {

    @TableId
    @TableField("course_id")
    private String courseId;

    @TableField("course_name")
    private String courseName;

    @TableField("course_type")
    private String courseType;

    @TableField("update_time")
    private LocalDateTime updateTime;

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseType='" + courseType + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public static CourseInfo newRow() {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseId("8");
        courseInfo.setCourseType("2");
        courseInfo.setCourseName("物理");
        courseInfo.setUpdateTime(LocalDateTime.now());
        return courseInfo;
    }
}
