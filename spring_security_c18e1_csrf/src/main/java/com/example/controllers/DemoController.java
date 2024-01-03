package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @GetMapping
    public String demo(){
        return "index.html";
    }

    @PostMapping("/smth")
    public String doSmth(){
        System.out.println("hola");
        return "index.html";
    }

    @PostMapping("/rest")
    @ResponseBody
    public String doRest(){
        return "rest way";
    }
}
