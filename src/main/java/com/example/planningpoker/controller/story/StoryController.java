package com.example.planningpoker.controller.story;

import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.controller.story.message.StoryDescriptionMessage;
import com.example.planningpoker.controller.story.message.StoryDescriptionUpdateMessage;
import com.example.planningpoker.controller.story.message.StoryMessage;
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
@CrossOrigin
public class StoryController {

    public static final String BACKEND_SOCKET_RESPONSE_FORMAT = "/backend-socket-response/%s";
    public static final String STORY_MESSAGE = "StoryMessage";
    public static final String STORY_DESCRIPTION_UPDATE_MESSAGE = "StoryDescriptionUpdateMessage";
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public StoryController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/story-create")
    public void createStory(@DestinationVariable String roomName, @DestinationVariable String userName, StoryDescriptionMessage storyDescriptionMessage){
        //TODO MIKA
        StoryMessage storyMessage = new StoryMessage(STORY_MESSAGE, "1", "storyTestDescription", new ArrayList<>(), null);
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), storyMessage);
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/story-update/description")
    public void updateStoryDescription(@DestinationVariable String roomName, @DestinationVariable String userName,
                                       @DestinationVariable String storyId, StoryDescriptionMessage storyDescriptionMessage){
        //TODO MIKA
        StoryDescriptionUpdateMessage msg = new StoryDescriptionUpdateMessage(STORY_DESCRIPTION_UPDATE_MESSAGE, storyId, storyDescriptionMessage.getDescription());
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
    }


    @GetMapping("/api/poker/{roomName}/{userName}/{storyId}/story-retrieve")
    @ResponseBody
    public void retrieveStory(@PathVariable String roomName, @PathVariable String userName, @PathVariable String storyId) {
        //TODO MIKA
        StoryMessage storyMessage = new StoryMessage(STORY_MESSAGE, storyId, "storyTestDescription", new ArrayList<>(), null);
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), storyMessage);
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/{storyId}/story-delete")
    public void deleteStory(@DestinationVariable String roomName, @DestinationVariable String userName,
                            @DestinationVariable String storyId) {
        //TODO MIKA
        messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), new SuccessMessage(true));
    }
}
