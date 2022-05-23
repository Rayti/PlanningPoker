package com.example.planningpoker.controller.story.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoryDescriptionUpdateMessage {
    private String type;
    private String storyId;
    private String description;
}
