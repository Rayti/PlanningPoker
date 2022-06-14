package com.example.planningpoker.controller.user.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StoryHistoryPartMessage {
    private String id;
    private String description;
    private String estimation;
    private List<TaskHistoryPartMessage> tasks;
}
