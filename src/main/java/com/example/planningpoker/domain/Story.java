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
    private Long id;
    private String description;
    private List<Task> tasks;

    public Task getTask(Long taskId) {
        return tasks.stream().filter(task -> task.getId().longValue() == taskId.longValue()).findFirst().orElse(null);
    }

    public void deleteTask(Long taskId){
        tasks = tasks.stream().filter(task -> task.getId().longValue() != taskId.longValue()).collect(Collectors.toList());
    }
}
