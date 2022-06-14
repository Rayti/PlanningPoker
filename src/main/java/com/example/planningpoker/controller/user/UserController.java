package com.example.planningpoker.controller.user;

import com.example.planningpoker.controller.MessageMapper;
import com.example.planningpoker.controller.SessionMessage;
import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.controller.user.message.UserGameHistoryMessage;
import com.example.planningpoker.domain.*;
import com.example.planningpoker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @GetMapping("/api/poker/user/game-history/{userName}")
    @ResponseBody
    public UserGameHistoryMessage retrieveUserGameHistory(@PathVariable String userName) {
        log.info("/api/poker/user/game-history/{}", userName);

        if(!userService.loggedUserExists(userName)){
            return new UserGameHistoryMessage(null);
        }

        if(userName.equals("TEST")){
            return testUserHistoryGameData();
        }

        return MessageMapper.toUserGameHistoryMessage(userService.getRegisteredUser(userName).getRoomGamesHistory());
    }

    private UserGameHistoryMessage testUserHistoryGameData(){
        User testUser = new User("TestUser", "password");
        Room testRoom = new Room("First funny game");
        Game testGame1 = new Game();
        testGame1.setStory(new Story(1L, "So Funny Story!", List.of(new Task(1L, "Not so funny task :("))));
        testGame1.setGameResult(5.5F);
        testRoom.getGames().add(testGame1);


        Room testRoom2 = new Room("Second funny game");
        Game testGame21 = new Game();
        testGame21.setGameResult(2.0F);
        testGame21.setStory(new Story(1L, "Magical Story?", List.of(new Task(1L, "Write Book"), new Task(2L, "Read Book"), new Task(3L, "Burn Book"))));

        Game testGame22 = new Game();
        testGame22.setGameResult(13.5F);
        testGame22.setStory(new Story(1L, "Uptown funky punk", List.of(new Task(1L, "Task numero UNO"), new Task(2L, "Task numero DOS"))));

        testRoom2.getGames().add(testGame21);
        testRoom2.getGames().add(testGame22);

        testUser.getRoomGamesHistory().add(testRoom);
        testUser.getRoomGamesHistory().add(testRoom2);
        return MessageMapper.toUserGameHistoryMessage(testUser.getRoomGamesHistory());
    }
}
