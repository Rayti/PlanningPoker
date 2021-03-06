package com.example.planningpoker.service;

import com.example.planningpoker.domain.Game;
import com.example.planningpoker.domain.Room;
import com.example.planningpoker.domain.Story;
import com.example.planningpoker.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@ApplicationScope
public class DefaultRoomService implements RoomService {

    public static final String ROOM_DOES_NOT_EXIST_LOG = "Room {} does not exist";
    private List<Room> roomCache = Collections.synchronizedList(new ArrayList<>());


    @Override
    public boolean createRoom(User user, String roomName) {
        if(getRoom(roomName) == null){
            Game game = new Game();
            Room room = new Room(roomName);
            room.getUsers().add(user);
            room.setCurrentGame(game);
            roomCache.add(room);
            user.getRoomGamesHistory().add(room);
            log.info("Room {} and User {} created", roomName, user.getName());
            return true;
        }
        return false;
    }


    @Override
    public boolean joinRoom(User user, String roomName) {
        Optional<Room> optionalRoom = roomCache.stream().filter(room -> room.getRoomName().equals(roomName)).findFirst();

        if (optionalRoom.isEmpty()) {
            return false;
        }

        Room room = optionalRoom.get();

        Optional<User> optionalUser = optionalRoom.get().getUsers().stream().filter(x -> x.getName().equals(user.getName())).findFirst();

        if (optionalUser.isEmpty()) {
            room.getUsers().add(user);
            user.getRoomGamesHistory().add(room);

            return true;
        }

        return false;
    }


    @Override
    public boolean canJoinRoom(String userName, String roomName) {
        Optional<Room> optionalRoom = roomCache.stream().filter(room -> room.getRoomName().equals(roomName)).findFirst();

        if (optionalRoom.isEmpty()) {
            return false;
        }

        Optional<User> optionalUser = optionalRoom.get().getUsers().stream().filter(user -> user.getName().equals(userName)).findFirst();
        return optionalUser.isEmpty();
    }

    @Override
    public boolean deleteRoom(User user, String roomName) {
        Room room = getRoom(roomName);
        if (room == null) {
            return false;
        }
        List<Room> roomsUpdated = roomCache.stream().filter(room1 -> !room1.getRoomName().equals(roomName)).collect(Collectors.toList());
        roomCache.clear();
        roomCache.addAll(roomsUpdated);
        return true;
    }


    @Override
    public boolean leaveRoom(String userName, String roomName) {
        Room room = getRoom(roomName);
        if (room == null) {
            return false;
        }

        User user = getUserFromRoom(room, userName);

        if (user == null) {
            return false;
        }

        List<User> usersWithoutOneUser = room.getUsers().stream().filter(user1 -> !user1.getName().equals(userName)).collect(Collectors.toList());
        room.getUsers().clear();
        room.getUsers().addAll(usersWithoutOneUser);
        return true;
    }


    @Override
    public List<Story> getStories(String roomName) {
        return getRoom(roomName).getStories();
    }

    @Override
    public Room getRoom(String roomName){
        Optional<Room> optionalRoom = roomCache.stream().filter(room -> room.getRoomName().equals(roomName)).findFirst();
        return optionalRoom.orElse(null);
    }

    @Override
    public Story chooseStoryToCurrentGame(String roomName, String storyId) {
        Room room = getRoom(roomName);
        if (room == null || room.getStories()==null) {
            return null;
        }
        Story story = room.getStories().stream().filter(st -> st.getId() == Long.parseLong(storyId)).findFirst().orElse(null);

        if (story == null) {
            return null;
        }

        room.getCurrentGame().setStory(story);
        return story;
    }

    private User getUserFromRoom(Room room, String userName){
        return room.getUsers().stream().filter(user -> user.getName().equals(userName)).findFirst().orElse(null);
    }
}
