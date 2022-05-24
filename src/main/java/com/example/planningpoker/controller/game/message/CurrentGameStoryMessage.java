package com.example.planningpoker.controller.game.message;

import com.example.planningpoker.controller.story.message.StoryMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentGameStoryMessage {
    private String type;
    private StoryMessage storyMessage;
}
