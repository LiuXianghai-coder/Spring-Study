package org.xhliu.transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhliu.springtransaction.entity.CourseInfo;
import org.xhliu.springtransaction.mapper.CourseInfoMapper;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@SpringBootTest
public class MapperTest {

    @Resource
    private CourseInfoMapper courseInfoMapper;

    @Test
    public void mapperTest() {
        CourseInfo courseInfo = courseInfoMapper.selectById(1);
        System.out.println(courseInfo);
    }
}
