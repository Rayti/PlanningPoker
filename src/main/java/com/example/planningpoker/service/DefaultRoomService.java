package com.example.planningpoker.service;

import com.example.planningpoker.controller.old.Message;
import com.example.planningpoker.controller.old.SelectCardMessage;
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
    public boolean createRoom(String userName, String roomName) {
        boolean notAlreadyExistsRoomName = roomCache.stream().noneMatch(room -> room.getRoomName().equals(roomName));
        if(notAlreadyExistsRoomName){
            Room room = new Room(roomName);
            User user = new User(userName, room);
            room.getUsersCache().add(user);
            roomCache.add(room);
            return true;
        }
        return false;
    }

    @Override
    public boolean joinRoom(String userName, String roomName) {
        Optional<Room> optionalRoom = roomCache.stream().filter(room -> room.getRoomName().equals(roomName)).findFirst();

        if (optionalRoom.isEmpty()) {
            return false;
        }

        Room room = optionalRoom.get();

        Optional<User> optionalUser = optionalRoom.get().getUsersCache().stream().filter(user -> user.getName().equals(userName)).findFirst();

        if (optionalUser.isEmpty()) {
            User user = new User(userName, room);
            room.getUsersCache().add(user);
            return true;
        }

        return false;
    }


    @Override
    public Message selectCard(SelectCardMessage selectCardMessage) {
        roomCache.stream()
                .filter(room -> selectCardMessage.getRoomName().equals(room.getRoomName()))
                .findFirst().get().getCurrentGame().getChosenCards();
        return null;
    }

    @Override
    public boolean deleteRoom(String userName, String roomName) {
        return false;
    }

    @Override
    public boolean leaveRoom(String userName, String roomName) {
        return false;
    }
}
