package com.example.planningpoker.domain;

import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
public class Game {
    private int id;
    private Story story;
    private Map<User, Card> chosenCards;
    private boolean gameFinished;
    private float gameResult;

    public Game() {
        this.chosenCards = Collections.synchronizedMap(new HashMap<>());
        this.gameFinished = false;
    }

    public boolean userAlreadyChoseCard(String userName){
        return chosenCards.entrySet().stream().filter(userCardEntry -> userCardEntry.getKey().getName().equals(userName)).findFirst().orElse(null) != null;
    }

    public void calculateAndSetGameResult() {
        gameResult = (float) chosenCards.values().stream()
                .mapToDouble(card -> card.getValue().equals("?") ? 0.0d : Double.parseDouble(card.getValue()))
                .average().orElse(0.0d);
    }
}
