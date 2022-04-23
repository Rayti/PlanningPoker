package com.example.planningpoker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World guys(This string comes from spring-java backend)!";
    }
}
