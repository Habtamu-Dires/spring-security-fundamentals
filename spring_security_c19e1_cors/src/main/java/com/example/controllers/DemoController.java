package com.example.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
//    @CrossOrigin("http://localhost:8080") // all specific origin
//    @CrossOrigin("*") // allow all origin
    public String demo(){
        return "Demo";
    }
}
