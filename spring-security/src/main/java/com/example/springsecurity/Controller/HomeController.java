package com.example.springsecurity.Controller;

import com.example.springsecurity.Configuration.PasswordBeans;
import com.example.springsecurity.Entity.UserRole;
import com.example.springsecurity.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Created by IntelliJ IDEA.
 * User: liuxianghai
 * Date: 2021/1/11
 * Time: 下午2:41
 */

@Controller
@RequestMapping(path = "/")
public class HomeController {
    @Autowired
    private UserRoleRepository roleRepository;
    
    /**
     * 注入 Authentication 对象到控制器方法中， 获取已认证的用户信息
     * @param authentication ： 注入的 Authentication 对象
     * @return ： 获取到的用户信息
     */
    @GetMapping(path = "/checkUserAuth")
    public @ResponseBody
    String checkUser(Authentication authentication) {
        UserRole userRole = (UserRole) authentication.getPrincipal();

        return userRole.toString();
    }

    /**
     * 使用注入Principal对象到控制器方法中， 获取已认证的用户信息
     * @param principal ： 注入的 Principal 对象
     * @return ： 得到的用户信息
     */
    @GetMapping(path = "/checkUserByPrincipal")
    public @ResponseBody
    String checkUserByPrincipal(Principal principal) {
        UserRole role = roleRepository.findUserRoleByUsername(principal.getName());

        assert role != null; // 用户应当是已经登录的， 所以它应当是已经存在的。

        return role.toString();
    }

    /**
     * 添加 @AuthenticationPrincipal 注解，
     * 表明这个 UserRole 对象是一个已经认证的 Principal 对象。
     * @param userRole ： 认证过的 principal 对象
     * @return ： 认证的 UserRole 对象
     */
    @GetMapping(path = "/checkUserByAuthAnnotation")
    public @ResponseBody
    String checkUserByAuthAnnotation(@AuthenticationPrincipal UserRole userRole) {
        return userRole.toString();
    }

    /**
     * 通过上下文来获取已认证的用户信息
     * @return : 根据上下问获取到的用户信息
     */
    @GetMapping(path = "/checkUserByContext")
    public @ResponseBody
    String checkUserByContext() {
        // 获取上下文的认证信息
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        UserRole userRole = (UserRole) authentication.getPrincipal();

        assert userRole != null;

        return userRole.toString();
    }
}
