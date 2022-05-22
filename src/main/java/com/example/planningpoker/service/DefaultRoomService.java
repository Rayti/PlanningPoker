package com.example.planningpoker.service;

import com.example.planningpoker.controller.Message;
import com.example.planningpoker.controller.SelectCardMessage;
import com.example.planningpoker.domain.Room;
import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@ApplicationScope
public class DefaultRoomService implements RoomService {

    private List<Room> roomCache = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Message createRoom(String userName, String roomName) {
        boolean notAlreadyExistsRoomName = roomCache.stream().noneMatch(room -> room.getRoomName().equals(roomName));
        if(notAlreadyExistsRoomName){
            Room room = new Room(roomName);
            User user = new User(userName, room);
            room.getUsersCache().add(user);
            roomCache.add(room);
            return new Message(String.format("Room %s created", roomName), true);
        }
        return new Message(String.format("Room %s already exists", roomName), false);
    }

    @Override
    public Message joinRoom(String userName, String roomName) {
        Optional<Room> optionalRoom = roomCache.stream().filter(room -> room.getRoomName().equals(roomName)).findFirst();

        if (optionalRoom.isEmpty()) {
            return new Message(String.format("Room %s does not exist.", roomName), false);
        }

        Room room = optionalRoom.get();

        Optional<User> optionalUser = optionalRoom.get().getUsersCache().stream().filter(user -> user.getName().equals(userName)).findFirst();

        if (optionalUser.isEmpty()) {
            User user = new User(userName, room);
            room.getUsersCache().add(user);
            return new Message(String.format("User %s joined the room %s", userName, roomName), true);
        }

        return new Message(String.format("User %s is already in the room %s", userName, roomName), false);
    }


    @Override
    public Message selectCard(SelectCardMessage selectCardMessage) {
        roomCache.stream()
                .filter(room -> selectCardMessage.getRoomName().equals(room.getRoomName()))
                .findFirst().get().getCurrentGame().getChosenCards();
        return null;
    }
}
