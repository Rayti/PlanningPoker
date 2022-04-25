package com.example.planningpoker.controller;

import com.example.planningpoker.domain.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping("/api/poker")
public class UserController {



    @GetMapping("/newUser/{userName}")
    public String newUser(@PathVariable String userName ){
        new User(userName);
        return "OK";
    }
}
