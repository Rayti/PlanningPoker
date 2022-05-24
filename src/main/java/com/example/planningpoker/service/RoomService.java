package com.example.planningpoker.service;

import org.springframework.stereotype.Service;

@Service
public interface RoomService {

    boolean createRoom(String userName, String roomName);

    boolean deleteRoom(String userName, String roomName);

    boolean leaveRoom(String userName, String roomName);

    boolean joinRoom(String userName, String roomName);
}
