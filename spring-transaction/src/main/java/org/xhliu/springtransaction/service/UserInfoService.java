package org.xhliu.springtransaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhliu.springtransaction.annotation.DataSource;
import org.xhliu.springtransaction.datasource.DataSourceHolder;
import org.xhliu.springtransaction.datasource.DataSourceType;
import org.xhliu.springtransaction.entity.UserInfo;
import org.xhliu.springtransaction.mapper.UserInfoMapper;

import javax.annotation.Resource;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lxh
 */
@Service
@Transactional
public class UserInfoService {

    private final static Logger log = LoggerFactory.getLogger(UserInfoService.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    @DataSource(DataSourceType.POSTGRESQL)
    public int updateUserInfo() {
        UserInfo obj = new UserInfo();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String s = String.valueOf(random.nextLong(0, Long.MAX_VALUE));
        s = s.substring(0, Math.min(32, s.length()));
        log.info("backup id = {}", s);
        obj.setUserId("100");
        obj.setBackUpId(s);
        return userInfoMapper.updateById(obj);
    }

    public int addUserInfo() {
        DataSourceHolder.setCurDataSource(DataSourceType.MYSQL);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("100001010");
        userInfo.setUserGender("Male");
        userInfo.setUserName("XXX");
        userInfo.setSimpleId("0x3f3f3f");
        return userInfoMapper.insert(userInfo);
    }

    public int delUserInfo() {
        DataSourceHolder.setCurDataSource(DataSourceType.MYSQL);
        return userInfoMapper.deleteById("100001010");
    }

    public UserInfo sampleUserInfo() {
        DataSourceHolder.setCurDataSource(DataSourceType.MYSQL);
        return userInfoMapper.selectById("100001010");
    }
}
