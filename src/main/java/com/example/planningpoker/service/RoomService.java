package com.example.planningpoker.service;

import com.example.planningpoker.controller.old.Message;
import com.example.planningpoker.controller.old.SelectCardMessage;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {

    boolean createRoom(String userName, String roomName);

    boolean deleteRoom(String userName, String roomName);

    boolean leaveRoom(String userName, String roomName);

    boolean joinRoom(String userName, String roomName);

    Message selectCard(SelectCardMessage selectCardMessage);

}
