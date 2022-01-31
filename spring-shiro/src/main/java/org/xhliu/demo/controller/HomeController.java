package org.xhliu.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xhliu.demo.dto.LoginDto;

/**
 * @author xhliu
 * @time 2022-01-29-下午5:31
 */
@Controller
public class HomeController {
    @RequestMapping(path = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("user", LoginDto.newInstance());
        return "index";
    }

    @RequestMapping(path = "/403")
    public String page403() {
        return "403";
    }

    @RequestMapping(path = "/view")
    public String view() {
        return "view";
    }
}
