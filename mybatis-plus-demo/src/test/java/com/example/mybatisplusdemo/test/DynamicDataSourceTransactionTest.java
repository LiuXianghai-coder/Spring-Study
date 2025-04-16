package com.example.mybatisplusdemo.test;

import com.example.mybatisplusdemo.MybatisPlusDemoApplication;
import com.example.mybatisplusdemo.entity.CourseInfo;
import com.example.mybatisplusdemo.entity.UserInfo;
import com.example.mybatisplusdemo.service.CourseInfoService;
import com.example.mybatisplusdemo.service.UserInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 *@author lxh
 */
@SpringBootTest(classes = MybatisPlusDemoApplication.class)
public class DynamicDataSourceTransactionTest {

    @Resource
    UserInfoService userInfoService;

    @Resource
    CourseInfoService courseInfoService;

    @Test
    public void transactionTest() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String backUpId = String.valueOf(random.nextInt());
        LocalDateTime updatedTime = LocalDateTime.now();
        Integer userId = 2, courseId = 1;

        Assertions.assertEquals(1, userInfoService.updateUserCourseInfo(userId, backUpId, courseId, updatedTime));

        UserInfo userInfo = userInfoService.findUserInfo(userId);
        Assertions.assertEquals(backUpId, userInfo.getBackupId());

        CourseInfo courseInfo = courseInfoService.findCourseInfo(courseId);
        LocalDateTime courseInfoUpdateTime = courseInfo.getUpdateTime();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Assertions.assertEquals(updatedTime.format(pattern), courseInfoUpdateTime.format(pattern));
    }
}
