package com.example.planningpoker.controller.story;

import com.example.planningpoker.controller.MessageMapper;
import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.controller.story.message.*;
import com.example.planningpoker.domain.Room;
import com.example.planningpoker.domain.Story;
import com.example.planningpoker.service.RoomService;
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

import java.util.ArrayList;

@Controller
@Slf4j
@CrossOrigin
public class StoryController {

    public static final String BACKEND_SOCKET_RESPONSE_FORMAT = "/backend-socket-response/%s";
    public static final String STORY_MESSAGE = "StoryMessage";
    public static final String STORY_DESCRIPTION_UPDATE_MESSAGE = "StoryDescriptionUpdateMessage";
    private SimpMessagingTemplate messagingTemplate;
    private RoomService roomService;

    @Autowired
    public StoryController(RoomService roomService, SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/story-create")
    public void createStory(@DestinationVariable String roomName, @DestinationVariable String userName, StoryDescriptionMessage storyDescriptionMessage){
        //DONE
        log.info("/api/poker/{}/{}/story-create", roomName, userName);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName)) {
            Story story = new Story(Integer.parseInt(storyDescriptionMessage.getId()), storyDescriptionMessage.getDescription(), new ArrayList<>());
            room.getStories().add(story);
            CreateStoryMessage msg = MessageMapper.toCreateStoryMessage(story);
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/story-create SENT CreateStoryMessage to subscribers", roomName, userName);
        }
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/story-update/description")
    public void updateStoryDescription(@DestinationVariable String roomName, @DestinationVariable String userName,
                                       @DestinationVariable String storyId, StoryDescriptionMessage storyDescriptionMessage){
        //DONE
        log.info("/api/poker/{}/{}/{storyId}/story-update/description", userName, roomName);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName) && room.getStory(Integer.parseInt(storyId)) != null) {
            Story story = room.getStory(Integer.parseInt(storyId));
            story.setDescription(storyDescriptionMessage.getDescription());
            StoryDescriptionUpdateMessage msg = new StoryDescriptionUpdateMessage(STORY_DESCRIPTION_UPDATE_MESSAGE, storyId, storyDescriptionMessage.getDescription());
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/{storyId}/story-update/description SENT {} to subscribers", userName, roomName, STORY_DESCRIPTION_UPDATE_MESSAGE);
        }
    }


    @GetMapping("/api/poker/{roomName}/{userName}/{storyId}/story-retrieve")
    @ResponseBody
    public StoryMessage retrieveStory(@PathVariable String roomName, @PathVariable String userName, @PathVariable String storyId) {
        //DONE
        log.info("/api/poker/{}/{}/{storyId}/story-retrieve", roomName, userName);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName) && room.getStory(Integer.parseInt(storyId)) != null) {
            return MessageMapper.toStoryMessage(room.getStory(Integer.parseInt(storyId)));
        }
        log.error("/api/poker/{}/{}/{storyId}/story-retrieve - no room or user", roomName, userName);
        return null;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/story-delete")
    public void deleteStory(@DestinationVariable String roomName, @DestinationVariable String userName,
                            @DestinationVariable String storyId) {
        //DOne
        log.info("/api/poker/{}/{}/{}/story-delete", roomName, userName, storyId);
        Room room = roomService.getRoom(roomName);
        if (room != null && room.userExists(userName) && room.getStory(Integer.parseInt(storyId)) != null) {
            room.deleteStory(Integer.parseInt(storyId));

            DeleteStoryMessage msg = new DeleteStoryMessage("DeleteStoryMessage", storyId);
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/{}/story-delete SENT DeleteStoryMessage to subscribers", roomName, userName, storyId);
        }
    }
}
