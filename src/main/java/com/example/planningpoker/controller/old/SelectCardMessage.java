package com.example.planningpoker.controller.old;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectCardMessage {
    private String roomName;
    private String userName;
    private String gameId;
    private String cardId;
    private String cardValue;
}
