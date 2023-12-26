package org.xhliu.springtransaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class UseInfoService {

    private final static Logger log = LoggerFactory.getLogger(UseInfoService.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    public int updateUserInfo() {
        DataSourceHolder.setCurDataSource(DataSourceType.POSTGRESQL);
        UserInfo obj = new UserInfo();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String s = String.valueOf(random.nextLong(0, Long.MAX_VALUE));
        s = s.substring(0, Math.min(32, s.length()));
        log.info("backup id = {}", s);
        obj.setUserId("100");
        obj.setBackUpId(s);
        return userInfoMapper.updateById(obj);
    }
}
