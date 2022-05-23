package com.example.planningpoker.controller.game;
import com.example.planningpoker.controller.game.message.GameDataMessage;
import com.example.planningpoker.controller.game.message.GameResultMessage;
import com.example.planningpoker.controller.game.message.SelectedCardMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@CrossOrigin
@Controller
public class GameController {

    public static final String BACKEND_SOCKET_RESPONSE_FORMAT = "/backend-socket-response/%s";
    public static final String SELECTED_CARD_MESSAGE = "SelectedCardMessage";
    public static final String GAME_DATA_MESSAGE = "GameDataMessage";
    public static final String GAME_RESULT_MESSAGE = "GameResultMessage";
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/next-game")
    public void goToNextGame(@DestinationVariable String roomName, @DestinationVariable String userName) {
        //TODO MIKA
        GameDataMessage gameDataMessage = new GameDataMessage(GAME_DATA_MESSAGE, "0", new ArrayList<>(), new ArrayList<>(), null);
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), gameDataMessage);
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/finish-game")
    public void finishGame(@DestinationVariable String roomName, @DestinationVariable String userName) {
        //TODO MIKA
        GameResultMessage msg = new GameResultMessage(GAME_RESULT_MESSAGE, "5.5", new ArrayList<>());
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
    }

    @GetMapping("/api/poker/{roomName}/{userName}/game-data")
    @ResponseBody
    public GameDataMessage getCurrentGameData(@PathVariable String roomName, @PathVariable String userName) {
        //TODO MIKA
        return new GameDataMessage(GAME_DATA_MESSAGE, "0", new ArrayList<>(), new ArrayList<>(), null);
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/select-card/{id}/{value}")
    public void selectCard(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String id, @DestinationVariable String value) {
        //TODO MIKA
        SelectedCardMessage msg = new SelectedCardMessage(SELECTED_CARD_MESSAGE, "testUser", "1", "4");
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
    }
}
