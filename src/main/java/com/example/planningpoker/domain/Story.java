package com.example.planningpoker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Story {
    private int id;
    private String description;
    private List<Task> tasks;

    public Task getTask(int taskId) {
        return tasks.stream().filter(task -> task.getId() == taskId).findFirst().orElse(null);
    }

    public void deleteTask(int taskId){
        tasks = tasks.stream().filter(task -> task.getId() != taskId).collect(Collectors.toList());
    }
}
