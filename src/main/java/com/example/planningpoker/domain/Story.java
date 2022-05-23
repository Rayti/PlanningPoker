package com.example.planningpoker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Story {
    private List<Task> tasks;
    private Task currentTask;
}
