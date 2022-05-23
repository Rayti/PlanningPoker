package com.example.planningpoker.domain;

import lombok.Data;

import java.util.*;

@Data
public class Game {
    private int id;
    private Task task;
    private List<User> users;
    private Map<User, Card> chosenCards;
    private boolean gameFinished;
    private float gameResult;

    public Game() {
        this.chosenCards = Collections.synchronizedMap(new HashMap<>());
        this.users = Collections.synchronizedList(new ArrayList<>());
        this.gameFinished = false;
    }
}
