package com.shop.controller.vc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    //@GetMapping("/hello")
    public String hello() {
        return "/hello";
    }

    @GetMapping("/")
    public String home1() {
        return "/index";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/login2")
    public String login2(Model model) {
        return "/login_2";
    }

    @GetMapping("/registration")
    public String registration() {
        return "/registration";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
