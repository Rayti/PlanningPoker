package com.example.planningpoker.controller;

import com.example.planningpoker.controller.game.message.SelectedCardMessage;
import com.example.planningpoker.controller.story.message.CreateStoryMessage;
import com.example.planningpoker.controller.story.message.StoryMessage;
import com.example.planningpoker.controller.task.message.TaskMessage;
import com.example.planningpoker.controller.user.message.*;
import com.example.planningpoker.domain.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public static UserGameHistoryMessage toUserGameHistoryMessage(List<Room> rooms) {
        int id = 1;
        List<RoomHistoryPartMessage> roomHistoryPartMessages = new ArrayList<>();
        for (Room roomIterator : rooms) {
            roomHistoryPartMessages.add(MessageMapper.toRoomHistoryPartMessage(id, roomIterator));
            id++;
        }
        return new UserGameHistoryMessage(roomHistoryPartMessages);
    }

    public static RoomHistoryPartMessage toRoomHistoryPartMessage(int id, Room room) {
        List<StoryHistoryPartMessage> storyHistoryPartMessages = room.getGames().stream().map(MessageMapper::toStoryHistoryPartMessage).collect(Collectors.toList());
        return new RoomHistoryPartMessage(String.valueOf(id), room.getRoomName(), storyHistoryPartMessages);
    }

    public static StoryHistoryPartMessage toStoryHistoryPartMessage(Game game) {
        List<TaskHistoryPartMessage> taskHistoryPartMessages = game.getStory().getTasks().stream().map(MessageMapper::toTaskHistoryPartMessage).collect(Collectors.toList());
        return new StoryHistoryPartMessage(game.getStory().getId().toString(), game.getStory().getDescription(), String.valueOf(game.getGameResult()), taskHistoryPartMessages);
    }

    public static TaskHistoryPartMessage toTaskHistoryPartMessage(Task task){
        return new TaskHistoryPartMessage(task.getId().toString(), task.getDescription());
    }
}