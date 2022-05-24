package com.example.planningpoker.service;

import com.example.planningpoker.domain.Game;
import com.example.planningpoker.domain.Room;
import com.example.planningpoker.domain.Story;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    boolean createRoom(String userName, String roomName);

    boolean deleteRoom(String userName, String roomName);

    boolean leaveRoom(String userName, String roomName);

    boolean joinRoom(String userName, String roomName);

    Room getRoom(String roomName);

    List<Story> getStories(String roomName);

    Story chooseStoryToCurrentGame(String roomName, String storyId);
}
