package org.xhliu.cloudnacos.demo;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author xhliu
 * @time 2022-02-27-下午5:07
 */
public class NacosDemo {
    private final static String SERVER_ADDRESS = "127.0.0.1:8848";

    private final static String SHIRO_CONFIG_DATA_ID = "spring-shiro-config";

    private final static String SCORE_DEV_NAMESPACE = "0af1887f-37e9-4a33-af1b-e874a0f26377";

    private final static String DEFAULT_GROUP = "DEFAULT_GROUP";

    private final static Long TIMEOUT_MS = 1000L;

    private final static Logger logger = LoggerFactory.getLogger(NacosDemo.class);

    public static void main(String[] args) {
        try {
            /*
                获取默认命名空间下的 SHIRO_CONFIG_DATA_ID 的配置内容
            */
            ConfigService configService = NacosFactory.createConfigService(SERVER_ADDRESS);
            String configContent = configService.getConfig(SHIRO_CONFIG_DATA_ID, DEFAULT_GROUP, TIMEOUT_MS);

            logger.info("content = {}", configContent);

            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, SERVER_ADDRESS);
            properties.put(PropertyKeyConst.NAMESPACE, SCORE_DEV_NAMESPACE);
            configService = NacosFactory.createConfigService(properties);

            /*
                获取 SCORE_DEV_NAMESPACE 命名空间下的 SHIRO_CONFIG_DATA_ID 对应的配置属性
             */
            String content2 = configService.getConfig(SHIRO_CONFIG_DATA_ID, DEFAULT_GROUP, TIMEOUT_MS);
            logger.info("content2 = {}", content2);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
