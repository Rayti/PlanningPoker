package com.example.planningpoker.controller.task.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteTaskMessage {
    private String type;
    private String id;
}
