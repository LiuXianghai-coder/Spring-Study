package org.xhliu.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xhliu.demo.common.AbstractHttpServletRequest;
import org.xhliu.demo.dto.LoginDto;
import org.xhliu.demo.entity.CustomerInfo;
import org.xhliu.demo.repository.CustomerRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author xhliu
 * @time 2022-01-22-下午5:03
 */
@Controller
public class UserController implements AbstractHttpServletRequest {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private final CustomerRepo customerRepo;

    static final String successUrl = "http://127.0.0.1:8080/view";

    static final String loginUrl = "http://127.0.0.1:8080/index";

    @Autowired
    public UserController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @GetMapping(path = "/user/{userId}")
    public @ResponseBody
    CustomerInfo user(@PathVariable Long userId) {
        Optional<CustomerInfo> optional = customerRepo.findById(userId);
        if (optional.isEmpty()) return null;

        CustomerInfo info = optional.get();
        System.out.println(info);
        return info;
    }

    @PostMapping(path = "/login")
    public String login(
            @ModelAttribute LoginDto user,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                user.getUserAccount(),
                user.getPassword()
        );

        try {
            subject.login(token);
            log.info("登录成功");
        } catch (Exception e) {
            if (e instanceof UnknownAccountException) {
                log.info("账户 " + token.getUsername() + " 不存在");
            } else if (e instanceof IncorrectCredentialsException) {
                log.info("账户名和密码不匹配");
            }

            e.printStackTrace();
            model.addAttribute("user", LoginDto.newInstance());
            return "index";
        }

        return "view";
    }
}
