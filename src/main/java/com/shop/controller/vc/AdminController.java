package com.shop.controller.vc;

import com.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

}
