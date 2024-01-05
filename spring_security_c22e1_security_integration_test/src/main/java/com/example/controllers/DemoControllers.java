package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoControllers {

    @GetMapping("/demo")
    public String demo(){
        return "demo";
    }
}
