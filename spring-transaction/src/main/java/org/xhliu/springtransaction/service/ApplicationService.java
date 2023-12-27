package org.xhliu.springtransaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author lxh
 */
@Service
@Transactional
public class ApplicationService {

    private final static Logger log = LoggerFactory.getLogger(ApplicationService.class);

    @Resource
    private UserInfoService userInfoService;

    public void bizHandler() {
        if (userInfoService.addUserInfo() > 0) {
            log.info("添加用户成功。。。。。。。");
        }
        log.info("查询到的样本用户 {}", userInfoService.sampleUserInfo());
        if (userInfoService.delUserInfo() > 0) {
            log.info("删除样本用户成功。。。。。。");
        }
        Assert.isNull(userInfoService.sampleUserInfo(), "删除后的样本用户记录应当不存在");
    }
}
