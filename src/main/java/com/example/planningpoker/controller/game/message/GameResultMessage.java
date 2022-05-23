package com.example.planningpoker.controller.game.message;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GameResultMessage {
    private String type;
    private String gameAvgValue;
    private List<GameResultCardSelected> selectedCards;
}
