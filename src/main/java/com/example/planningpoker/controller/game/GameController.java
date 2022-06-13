package com.example.planningpoker.controller.game;

import com.example.planningpoker.controller.MessageMapper;
import com.example.planningpoker.controller.game.message.*;
import com.example.planningpoker.controller.story.message.StoryMessage;
import com.example.planningpoker.controller.user.message.UserMessage;
import com.example.planningpoker.domain.*;
import com.example.planningpoker.service.RoomService;
import com.example.planningpoker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@Controller
public class GameController {

    public static final String BACKEND_SOCKET_RESPONSE_FORMAT = "/backend-socket-response/%s";
    public static final String SELECTED_CARD_MESSAGE = "SelectedCardMessage";
    public static final String GAME_DATA_MESSAGE = "GameDataMessage";
    public static final String GAME_RESULT_MESSAGE = "GameResultMessage";
    private SimpMessagingTemplate messagingTemplate;
    private RoomService roomService;
    private UserService userService;

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate, RoomService roomService, UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
        this.userService = userService;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/next-game")
    public void goToNextGame(@DestinationVariable String roomName, @DestinationVariable String userName) {
        log.info("/api/poker/{}/{}/next-game", roomName, userName);
        if(!userService.loggedUserExists(userName)){
            return;
        }

        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)) {
            room.setCurrentGame(new Game());
            NextGameMessage msg = new NextGameMessage("NextGameMessage");
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/next-game SENT NextGameMessage to subscribers", roomName, userName);
        }
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/finish-game")
    public void finishGame(@DestinationVariable String roomName, @DestinationVariable String userName) {
        log.info("/api/poker/{}/{}/finish-game", roomName, userName);
        if(!userService.loggedUserExists(userName)){
            return;
        }

        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)) {
            room.getCurrentGame().setGameFinished(true);

            List<SelectedCardMessage> selectedCardMessages = room.getCurrentGame().getChosenCards().entrySet().stream()
                    .map(userCardEntry -> MessageMapper.toSelectedCardMessage(userCardEntry.getKey(), userCardEntry.getValue())).collect(Collectors.toList());

            room.getCurrentGame().calculateAndSetGameResult();

            GameResultMessage msg = new GameResultMessage(GAME_RESULT_MESSAGE, String.valueOf(room.getCurrentGame().getGameResult()), selectedCardMessages);
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/finish-game SENT {} to subscribers", roomName, userName, GAME_RESULT_MESSAGE);
        }

    }


    @GetMapping("/api/poker/{roomName}/{userName}/game-data")
    @ResponseBody
    public GameDataMessage getCurrentGameData(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/game-data", roomName, userName);
        if(!userService.loggedUserExists(userName)){
            return new GameDataMessage("AuthenticationError", null, null, null, false, null);
        }

        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)) {

            List<StoryMessage> storyMessages = room.getStories().stream().filter(Objects::nonNull).map(MessageMapper::toStoryMessage).collect(Collectors.toList());

            List<UserMessage> userMessages = room.getUsers().stream().filter(Objects::nonNull).map(MessageMapper::toUserMessage).collect(Collectors.toList());
            Story currentStory = room.getCurrentGame().getStory();

            StoryMessage currentStoryMessage = MessageMapper.toStoryMessage(room.getCurrentGame().getStory());

            return new GameDataMessage(GAME_DATA_MESSAGE, storyMessages, userMessages, currentStoryMessage, room.getCurrentGame().isGameFinished(), String.valueOf(room.getCurrentGame().getGameResult()));
        }
        log.error("/api/poker/{}/{}/game-data - no room or user", roomName, userName);
        return null;
    }


    @MessageMapping("/api/poker/{roomName}/{userName}/select-card/{id}/{value}")
    public void selectCard(@DestinationVariable String roomName, @DestinationVariable String userName,
                           @DestinationVariable String id, @DestinationVariable String value) {
        log.info("/api/poker/{}/{}/select-card/{}/{}", roomName, userName, id, value);
        if(!userService.loggedUserExists(userName)){
            return;
        }

        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)) {
            String addLog = "user selected card for the first time";
            if (room.getCurrentGame().userAlreadyChoseCard(userName)){
                room.getCurrentGame().getChosenCards().remove(new User(userName, ""));
                addLog = "user selected card once more";
            }
            room.getCurrentGame().getChosenCards().put(new User(userName, ""), new Card(Integer.parseInt(id), value));
            SelectedCardMessage msg = new SelectedCardMessage(SELECTED_CARD_MESSAGE, userName, id, value);
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/select-card/{}/{} SENT SelectedCardMessage to subscribers\n{}", roomName, userName, id, value, addLog);
        }

    }


    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}choose-story")
    public void chooseStoryToCurrentGame(@DestinationVariable String roomName, @DestinationVariable String userName,
                                         @DestinationVariable String storyId) {
        log.info("/api/poker/{}/{}/{}choose-story", roomName, userName, storyId);
        if(!userService.loggedUserExists(userName)){
            return;
        }

        Story story = roomService.chooseStoryToCurrentGame(roomName, storyId);
        if (story != null && roomService.getRoom(roomName).userExists(userName)) {
            CurrentGameStoryMessage msg = new CurrentGameStoryMessage("CurrentGameStoryMessage", MessageMapper.toStoryMessage(story));
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/{}choose-story SENT CurrentGameStoryMessage to subscribers", roomName, userName, storyId);
        }
    }
}
