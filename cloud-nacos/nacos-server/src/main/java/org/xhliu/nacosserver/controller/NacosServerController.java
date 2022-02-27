package org.xhliu.nacosserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xhliu
 * @time 2022-02-27-下午7:50
 */
@RestController
@RefreshScope(proxyMode = ScopedProxyMode.NO)
public class NacosServerController {
    @Resource
    private ConfigurableApplicationContext applicationContext;

    @Value("${student.name:null}")
    private String name;

    @GetMapping(path = "/name")
    public String getName() {
        String name1 = applicationContext.getEnvironment()
                .getProperty("student.name");

        return String.format("name=%s, name1=%s", name1, name1);
    }
}
