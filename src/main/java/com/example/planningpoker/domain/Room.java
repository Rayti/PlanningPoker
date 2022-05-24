package com.example.planningpoker.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Room {

    private String roomName;
    private List<Game> games;
    private List<User> users;
    private List<Story> stories;
    private Game currentGame;

    public Room(String roomName) {
        this.roomName = roomName;
        this.games = new ArrayList<>();
        this.users = new ArrayList<>();
    }
}
