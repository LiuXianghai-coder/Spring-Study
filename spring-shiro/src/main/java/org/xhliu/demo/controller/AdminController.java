package org.xhliu.demo.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xhliu
 * @time 2022-01-29-下午12:01
 */
@Controller
public class AdminController {
    @RequiresRoles("admin")
    @RequestMapping(path = "/admin/config")
    public String admin() {
        return "view";
    }
}
