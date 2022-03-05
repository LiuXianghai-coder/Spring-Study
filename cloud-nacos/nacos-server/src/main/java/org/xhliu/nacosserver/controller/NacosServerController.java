package org.xhliu.nacosserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xhliu
 * @time 2022-02-27-下午7:50
 */
@RefreshScope
@RestController
public class NacosServerController {
    private final static Logger log = LoggerFactory.getLogger(NacosServerController.class);

    @Resource
    private ConfigurableApplicationContext applicationContext;

    // @NacosValue 注解不生效。。。。。
    @Value("${student.name:null}")
    private String name;

    @Value("${config2.name:null}")
    private String config2Name;

    @Value("${config3.name:null}")
    private String config3Name;

    @Value("${config4.name:null}")
    private String config4Name;

    @GetMapping("/name")
    public String getName() {
        String name1 = applicationContext.getEnvironment().getProperty("student.name");
        log.info("names = {}", name1);
//        String name2 = applicationContext.getEnvironment().getProperty("config1.name");
//        String name3 = applicationContext.getEnvironment().getProperty("config2.name");
        return String.format("name=%s<br> config2ame=%s<br> config3ame=%s<br> config4ame=%s",
                name, config2Name, config3Name, config4Name
        );
    }
}
