package com.example.planningpoker.controller.task.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTaskMessage {
    private String type;
    private String storyId;
    private String id;
    private String description;
}
