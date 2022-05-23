package com.example.planningpoker.controller.game.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectedCardMessage {
    private String type;
    private String userName;
    private String id;
    private String value;
}
