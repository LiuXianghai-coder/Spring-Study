package org.xhliu.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.xhliu.mybatis.vo.Course;

/**
 * @author xhliu
 * @time 2022-03-03-下午10:45
 */
@Mapper
public interface CourseMapper {
    @Select("SELECT * FROM course WHERE course_id=#{courseId}")
    Course getCourse(@Param("courseId") long courseId);
}
