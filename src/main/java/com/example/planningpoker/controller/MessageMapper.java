package com.example.planningpoker.controller;

import com.example.planningpoker.controller.game.message.SelectedCardMessage;
import com.example.planningpoker.controller.story.message.CreateStoryMessage;
import com.example.planningpoker.controller.story.message.StoryMessage;
import com.example.planningpoker.controller.task.message.TaskDescriptionMessage;
import com.example.planningpoker.controller.task.message.TaskMessage;
import com.example.planningpoker.controller.user.message.UserMessage;
import com.example.planningpoker.domain.Card;
import com.example.planningpoker.domain.Story;
import com.example.planningpoker.domain.Task;
import com.example.planningpoker.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageMapper {
    public static TaskMessage toTaskMessage(Task task) {
        return new TaskMessage("TaskMessage", String.valueOf(task.getId()), task.getDescription());
    }

    public static Task fromTaskMessage(TaskMessage taskMessage) {
        return new Task( Long.parseLong(taskMessage.getId()), taskMessage.getDescription());
    }

    public static StoryMessage toStoryMessage(Story story) {
        if(story != null ){
            List<TaskMessage> taskMessages = story.getTasks().stream().filter(Objects::nonNull).map(MessageMapper::toTaskMessage).collect(Collectors.toList());
            return new StoryMessage("StoryMessage", String.valueOf(story.getId()), story.getDescription(), taskMessages);
        }else{
            return new StoryMessage("StoryMessage", "", "", new ArrayList<>());
        }

    }

    public static CreateStoryMessage toCreateStoryMessage(Story story) {
        StoryMessage storyMessage = toStoryMessage(story);
        return new CreateStoryMessage("CreateStoryMessage", storyMessage.getId(), storyMessage.getDescription(), storyMessage.getTasks());
    }

    public static UserMessage toUserMessage(User user) {
        return new UserMessage("UserMessage", user.getName());
    }

    public static SelectedCardMessage toSelectedCardMessage(User user, Card card) {
        return new SelectedCardMessage("SelectedCardMessage", user.getName(), String.valueOf(card.getId()), card.getValue());
    }
}