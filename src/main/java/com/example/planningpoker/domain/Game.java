package com.example.planningpoker.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Game {
    private int id;
    private Task task;
    private List<User> users;
    private Map<User, Card> chosenCards;
    private boolean gameFinished;
    private float gameResult;

}
