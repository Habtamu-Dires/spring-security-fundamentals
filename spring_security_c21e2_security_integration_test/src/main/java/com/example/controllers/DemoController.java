package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo(){
        return "demo";
    }

    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }
}
