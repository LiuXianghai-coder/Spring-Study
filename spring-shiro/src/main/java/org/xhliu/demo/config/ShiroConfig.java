package org.xhliu.demo.config;

import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xhliu
 * @time 2022-01-28-下午8:53
 */
@Configuration
@Import({
        ShiroBeanConfiguration.class,
        ShiroConfiguration.class,
        ShiroAnnotationProcessorConfiguration.class
})
public class ShiroConfig {
}
