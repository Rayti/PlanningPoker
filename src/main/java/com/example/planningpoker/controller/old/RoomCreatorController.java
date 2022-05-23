package com.example.planningpoker.controller.old;

import com.example.planningpoker.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomCreatorController {

    private RoomService roomService;

    @Autowired
    public RoomCreatorController(RoomService roomService) {
        this.roomService = roomService;
    }


    /**
     * @return Message with true if created, false if not
     */
    @GetMapping("/api/poker/create-room")
    public Message createRoom(@RequestParam String name, @RequestParam String roomName) {
        //return roomService.createRoom(name, roomName);
        return null;
    }
}
