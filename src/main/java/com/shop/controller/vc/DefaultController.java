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
        return "/home";
    }

    @GetMapping("index")
    public String index() {
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

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
