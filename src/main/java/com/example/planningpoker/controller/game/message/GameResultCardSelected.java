package com.example.planningpoker.controller.game.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameResultCardSelected {
    private String userName;
    private String cardId;
    private String cardValue;
}
