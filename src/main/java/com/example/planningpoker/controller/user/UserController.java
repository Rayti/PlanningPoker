package com.example.planningpoker.controller.user;

import com.example.planningpoker.controller.SessionMessage;
import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/poker/user/register/{userName}/{password}")
    @ResponseBody
    public SuccessMessage registerUser(@PathVariable String userName, @PathVariable String password) {
        log.info("api/poker/user/register/{}/{}", userName, password);
        boolean success = userService.registerUser(userName, password);
        String logInfo = success ? "User {} registered" : "Not able to register user {}";
        log.info(logInfo, userName);
        return new SuccessMessage(success);
    }

    @GetMapping("api/poker/user/delete/{userName}")
    @ResponseBody
    public SuccessMessage deleteUser(@PathVariable String userName) {
        log.info("api/poker/user/delete/{}", userName);
        boolean success = userService.deleteUser(userName);
        String logInfo = success ? "User {} deleted" : "Not able to delete user {}";
        log.info(logInfo, userName);
        return new SuccessMessage(success);
    }

    @GetMapping("api/poker/user/check-existence/{userName}")
    @ResponseBody
    public SuccessMessage userExists(@PathVariable String userName) {
        log.info("api/poker/user/check-exist/{}", userName);
        boolean success = userService.userExists(userName);
        String logInfo = success ? "User {} exists" : "User {} does not exist";
        log.info(logInfo, userName);
        return new SuccessMessage(success);
    }

    @GetMapping("/api/poker/user/log-in/{userName}/{password}")
    @ResponseBody
    public SessionMessage logIn(@PathVariable String userName, @PathVariable String password) {
        log.info("/api/poker/user/log-in/{}/{}", userName, password);

        String sessionId = userService.logIn(userName, password);

        if (sessionId == null) {
            log.info("User {} could not log in", userName);
            return new SessionMessage("");
        }
        log.info("User {} logged in; sessionId: {}", userName, sessionId);
        return new SessionMessage(sessionId);
    }

    @GetMapping("api/poker/user/log-out/{userName}/{sessionId}")
    @ResponseBody
    public SuccessMessage logOut(@PathVariable String userName, @PathVariable String sessionId) {
        log.info("/api/poker/user/log-out/{}/{}", userName, sessionId);

        if (!userService.checkSessionId(sessionId, userName)) {
            log.warn("Attempt to access with wrong sessionId");
            return new SuccessMessage(false);
        }

        boolean success = userService.logOut(userName);
        String logInfo = success ? "User {} logged out" : "Not able to log out user {}";
        log.info(logInfo, userName);
        return new SuccessMessage(success);
    }
}
