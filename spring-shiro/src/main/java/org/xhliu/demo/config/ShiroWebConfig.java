package org.xhliu.demo.config;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xhliu
 * @time 2022-01-28-下午10:10
 */
@Configuration
@Import({
        ShiroBeanConfiguration.class,
        ShiroAnnotationProcessorConfiguration.class,
        ShiroWebConfiguration.class,
        ShiroWebFilterConfiguration.class,
        ShiroRequestMappingConfig.class
})
public class ShiroWebConfig {
    @Bean
    public HashedCredentialsMatcher matcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256"); // 使用 SHA-256 对信息进行散列（消息摘要）
        matcher.setHashIterations(10); // 对消息散列 10 次

        return matcher;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // 进入到 /admin 路径的请求必须具有 'admin' 的角色
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // 进入到 /docs 路径的用户必须具有读取权限
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        // 所有 /user 的请求都必须经过认证
        chainDefinition.addPathDefinition("/user", "authc");

        chainDefinition.addPathDefinition("/view", "authc");

        // 对于 /tmp 的请求可以是匿名的（即不需要经过认证）
        chainDefinition.addPathDefinition("/**", "anon");

        return chainDefinition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(
            SecurityManager manager,
            ShiroFilterChainDefinition chainDefinition
    ) {
        ShiroFilterFactoryBean resBean = new ShiroFilterFactoryBean();

        /*
            配置 SecurityManager，这里的 SecurityManager 是通过注入来实现的，
            因为我们已经将 ShiroConfiguration 加载到 Spring 容器中了，ShiroConfiguration 中定义了 SecurityManager  Bean
         */
        resBean.setSecurityManager(manager);

        /*
    	    如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
    	    但是如果此时项目为 Spring Boot 项目，那么该配置将会失效，
    	    考虑在 application.properties 或 application.yml 文件中进行配置
        */
        resBean.setLoginUrl("/index");

        // 登录成功后要跳转的路径
        resBean.setSuccessUrl("/view");

        // 未授权界面的重定向访问路径
        resBean.setUnauthorizedUrl("/403");

        resBean.setFilterChainDefinitionMap(chainDefinition.getFilterChainMap());

        return resBean;
    }
}
