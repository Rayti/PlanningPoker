package com.example.planningpoker.controller.old;

import com.example.planningpoker.domain.Card;
import com.example.planningpoker.domain.Game;
import com.example.planningpoker.domain.Room;
import com.example.planningpoker.domain.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
public class OverloadedPrototypeController {

/*    private SimpMessagingTemplate template;

    private Game game;

    @GetMapping("/api/poker/test")
    @ResponseBody
    public Message test(){
        System.out.println("test");
        return new Message("test git", true);
    }


    @GetMapping("/api/poker/{roomName}/{userName}/join-room")
    @ResponseBody
    public Message joinRoom(@PathVariable String roomName, @PathVariable String userName){
        game.getUsers().add(new User(userName, new Room(roomName)));
        return new Message("User joined room", true);
    }

    @GetMapping("/api/poker/{roomName}/{userName}/select-card/{id}/{value}")
    @ResponseBody
    public Message selectCard(@PathVariable String roomName, @PathVariable String userName, @PathVariable String id, @PathVariable String value) {
        User user = game.getUsers().stream().filter(x -> x.getName().equals(userName)).findFirst().get();

        if (game.getChosenCards().containsKey(user)) {
            return new Message("Card already selected", false);
        }

        game.getChosenCards().put(user, new Card(Integer.parseInt(id), Float.parseFloat(value)));
        return new Message("Card selected", true);

    }

    @GetMapping("/api/poker/{roomName}/{userName}/reveal")
    @ResponseBody
    public SelectedCardsMessage revealCards(@PathVariable String roomName, @PathVariable String userName) {
        List<Integer> chosenCardsIds = game.getChosenCards().entrySet().stream().map(userCardEntry -> userCardEntry.getValue().getId()).collect(Collectors.toList());
        game.setGameFinished(true);
        return new SelectedCardsMessage(chosenCardsIds);
    }*/
}
