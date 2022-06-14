package com.example.planningpoker.service;

import com.example.planningpoker.domain.Room;
import com.example.planningpoker.domain.Story;
import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    boolean createRoom(User user, String roomName);

    boolean deleteRoom(User user, String roomName);

    boolean leaveRoom(String userName, String roomName);

    boolean joinRoom(User user, String roomName);

    boolean canJoinRoom(String userName, String roomName);

    Room getRoom(String roomName);

    List<Story> getStories(String roomName);

    Story chooseStoryToCurrentGame(String roomName, String storyId);
}
