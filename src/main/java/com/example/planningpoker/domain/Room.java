package com.example.planningpoker.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Room {

    private String roomName;
    private List<Game> gamesCache;
    private List<User> usersCache;
    private List<Task> tasksCache;
    private Game currentGame;

    public Room(String roomName) {
        this.roomName = roomName;
        this.gamesCache = new ArrayList<>();
        this.usersCache = new ArrayList<>();
        this.tasksCache = new ArrayList<>();
    }
}
