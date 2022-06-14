package com.example.planningpoker.controller.user.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoomHistoryPartMessage {
    private String id;
    private String name;
    private List<StoryHistoryPartMessage> stories;
}
