package com.example.planningpoker.service;

import com.example.planningpoker.controller.Message;
import com.example.planningpoker.controller.SelectCardMessage;
import com.example.planningpoker.domain.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface RoomService {

    Message createRoom(String userName, String roomName);

    Message joinRoom(String userName, String roomName);

    Message selectCard(SelectCardMessage selectCardMessage);

}
