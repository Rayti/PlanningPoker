package com.example.planningpoker.controller.game.message;


import com.example.planningpoker.domain.Story;
import com.example.planningpoker.domain.Task;
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
    private String id;
    private List<Story> stories;
    private List<User> users;
    private Story currentStory;
}
