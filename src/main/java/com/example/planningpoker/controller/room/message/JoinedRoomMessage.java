package com.example.planningpoker.controller.room.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinedRoomMessage {
    private String type;
    private String userName;
}
