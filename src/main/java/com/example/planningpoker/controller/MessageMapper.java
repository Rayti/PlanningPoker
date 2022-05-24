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

import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {
    public static TaskMessage toTaskMessage(Task task) {
        return new TaskMessage("TaskMessage", String.valueOf(task.getId()), task.getDescription());
    }

    public static StoryMessage toStoryMessage(Story story) {
        List<TaskMessage> taskMessages = story.getTasks().stream().map(MessageMapper::toTaskMessage).collect(Collectors.toList());
        return new StoryMessage("StoryMessage", String.valueOf(story.getId()), story.getDescription(), taskMessages);
    }

    public static CreateStoryMessage toCreateStoryMessage(Story story) {
        StoryMessage storyMessage = toStoryMessage(story);
        return new CreateStoryMessage("CreateStoryMessage", storyMessage.getId(), storyMessage.getDescription(), storyMessage.getTasks());
    }

    public static UserMessage toUserMessage(User user) {
        return new UserMessage("UserMessage", user.getName());
    }

    public static SelectedCardMessage toSelectedCardMessage(User user, Card card) {
        return new SelectedCardMessage("SelectedCardMessage", user.getName(), String.valueOf(card.getId()), String.valueOf(card.getValue()));
    }
}