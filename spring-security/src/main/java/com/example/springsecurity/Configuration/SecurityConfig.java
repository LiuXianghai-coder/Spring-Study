package com.example.springsecurity.Configuration;

import com.example.springsecurity.Service.UserRoleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: liuxianghai
 * Date: 2021/1/11
 * Time: 下午2:36
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    private final UserRoleDetailService userRoleDetailService;

    @Autowired
    public SecurityConfig(DataSource dataSource,
                          UserRoleDetailService userRoleDetailService) {
        this.dataSource = dataSource;
        this.userRoleDetailService = userRoleDetailService;
    }

    /*
     * 使用 JDBC 以及自定义 SQL 语句实现对于用户的验证
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT user_name, user_password, is_enabled " +
//                        "FROM user_role WHERE user_name=?")
//                .authoritiesByUsernameQuery("SELECT user_name, user_role " +
//                        "FROM user_role WHERE user_name=?")
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }


    /*
        基于内存中的用户认证对象实现对于用户的验证
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("Jack")
//                .password("{bcrypt}$2a$10$o/SQpYXDmLUrGf0IpB/.kOm1y9HSWzNDCxQMXrUTAfDxegNIxPapK")
//                .authorities("USER_ROLE");
//    }


    /*
        使用用户自定义的验证方式进行验证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userRoleDetailService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/wonderful")
                    .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/home", "/login")
                    .access("permitAll()")
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/better")
                        .failureUrl("/error")
                .and()
                    .logout()
                    .logoutSuccessUrl("/home")
                    .permitAll();
    }
}
