package com.example.planningpoker.domain;

import lombok.Data;

import java.util.List;

@Data
public class Room {

    private String roomName;
    private List<Game> gamesCache;
    private List<User> usersCache;
    private List<Task> tasksCache;
}
