package com.example.planningpoker.controller.old;

import com.example.planningpoker.domain.User;
import com.example.planningpoker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/poker")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/newUser/{userName}")
    public String newUser(@PathVariable String userName ){
        userService.createUser(userName);
        return "OK";
    }

    @GetMapping("/{userName}/{cardId}")
    public boolean selectCard(@PathVariable String userName, @PathVariable int cardId){
        //userService.selectCard();
        return false;
    }
}
