package org.xhliu.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.ServiceInstance;


import javax.annotation.Resource;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-02-24-13:07
 **/
@RestController
public class EurekaClientController {
    private final static Logger log = LoggerFactory.getLogger(EurekaClientController.class);

    @Resource
    private DiscoveryClient client;

    @RequestMapping(path = "/service-instance/{applicationName}")
    public List<ServiceInstance>
    listInstancesByAppName(
            @PathVariable String applicationName
    ) {
        List<ServiceInstance> instances = client.getInstances(applicationName);
        instances.forEach(obj -> log.info("getInstance= {}", obj.getServiceId()));
        return instances;
    }
}
