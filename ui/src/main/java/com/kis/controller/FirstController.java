package com.kis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wanglong on 16-1-30.
 */
@Controller
public class FirstController {
    @RequestMapping("/")
    public String TestCo(){
        System.out.println("test");
        return "/index";
    }
}
