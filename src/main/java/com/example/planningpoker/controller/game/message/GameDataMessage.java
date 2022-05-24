package com.example.planningpoker.controller.game.message;


import com.example.planningpoker.controller.story.message.StoryMessage;
import com.example.planningpoker.controller.user.message.UserMessage;
import com.example.planningpoker.domain.Story;
import com.example.planningpoker.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GameDataMessage {
    private String type;
    private List<StoryMessage> stories;
    private List<UserMessage> users;
    private StoryMessage currentStory;
    private boolean isFinished;
    private String resultAvg;
}
