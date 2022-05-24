package com.example.planningpoker.controller.story.message;

import com.example.planningpoker.controller.task.message.TaskMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateStoryMessage {
    private String type;
    private String id;
    private String description;
    private List<TaskMessage> tasks;
}
