package org.xhliu.mybatis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhliu.mybatis.mapper.CourseMapper;
import org.xhliu.mybatis.vo.Course;

import javax.annotation.Resource;

/**
 * @author xhliu
 * @time 2022-03-03-下午10:47
 */
@SpringBootTest
public class MyBatisTest {
    @Resource
    private CourseMapper courseMapper;

    private final static Logger log = LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void test() {
        Course course = courseMapper.getCourse(1L);
        log.info("course = {}", course);
    }
}
