package com.example.planningpoker.controller;

import com.example.planningpoker.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomController {

    private RoomService roomService;
    private SimpMessagingTemplate template;

    @Autowired
    public RoomController(SimpMessagingTemplate template, RoomService roomService) {
        this.roomService = roomService;
        this.template = template;
    }



    @GetMapping("/api/poker/join-room")
    public Message joinRoom(@RequestParam String userName, @RequestParam String roomName) {
        return roomService.joinRoom(userName, roomName);
    }


    @MessageMapping("/select-card")
    public Message selectCard(SelectCardMessage selectCardMessage) {
        return null;

    }

}
