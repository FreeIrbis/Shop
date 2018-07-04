package com.shop.controller;

import com.shop.pojo.test.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;

@RestController
@RequestMapping("/test")
public class HelloTestController {

    @Autowired
    ServletContext context;

    @RequestMapping(
            value = "/hello",
            method = RequestMethod.GET
    )
    public Hello greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

}
