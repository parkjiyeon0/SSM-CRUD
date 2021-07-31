package com.atguigu.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author
 * @Date 2021/7/28 12:49
 * @Description
 */
@Controller
public class testController {

//    @RequestMapping("/test")
    public void test(String name){
        System.out.println(name);
    }

//    @RequestMapping("/test1")
    public void test1(){
        System.out.println("test1");
    }
}
