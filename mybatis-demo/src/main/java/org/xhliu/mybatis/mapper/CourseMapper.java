package org.xhliu.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.xhliu.mybatis.vo.Course;

/**
 * @author xhliu
 * @time 2022-03-03-下午10:45
 */
@Mapper
public interface CourseMapper {
    @Results({
            @Result(column = "course_name", property = "courseName"),
            @Result(column = "course_id", property = "courseId")
    })
    @Select("SELECT * FROM course WHERE course_id=#{courseId}")
    Course getCourse(@Param("courseId") long courseId);
}
