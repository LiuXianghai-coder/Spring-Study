package org.xhliu.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-02-24-15:08
 **/
@RestController
public class EurekaProducerController {
    private final static Logger log = LoggerFactory.getLogger(EurekaProducerController.class);

    @Resource
    private DiscoveryClient client;

    @Resource
    private Registration registration;

    @GetMapping(path = "/produce")
    public List<ServiceInstance> produce() {
        List<ServiceInstance> instances = client.getInstances(registration.getServiceId());
        instances.forEach(obj -> log.info("host={} service={}", obj.getHost(), obj.getServiceId()));
        return instances;
    }
}
