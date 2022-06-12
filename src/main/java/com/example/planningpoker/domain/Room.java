package com.example.planningpoker.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Room {

    private String roomName;
    private List<Game> games;
    private List<User> users;
    private List<Story> stories;
    private Game currentGame;

    public Room(String roomName) {
        this.roomName = roomName;
        this.games = new ArrayList<>();
        this.users = new ArrayList<>();
        this.stories = new ArrayList<>();
    }

    public boolean userExists(String userName){
        return users.stream().filter(user -> user.getName().equals(userName)).findFirst().orElse(null) != null;
    }

    public Story getStory(Long storyId) {
        return stories.stream().filter(story -> story.getId() == storyId).findFirst().orElse(null);
    }

    public void deleteStory(Long storyId) {
        stories = stories.stream().filter(x -> x.getId() != storyId).collect(Collectors.toList());
    }
}
