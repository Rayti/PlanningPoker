package com.example.planningpoker.controller.room;

import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(SimpMessagingTemplate template, RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/api/poker/{roomName}/{userName}/join-room")
    @ResponseBody
    public SuccessMessage joinRoom(@PathVariable String roomName, @PathVariable String userName){
        log.info("/api/poker/{}/{}/join-room", roomName, userName);
        return new SuccessMessage(this.roomService.joinRoom(userName, roomName));
    }


    @GetMapping("/api/poker/{roomName}/{userName}/leave-room")
    @ResponseBody
    public SuccessMessage leaveRoom(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/leave-room", roomName, userName);
        return new SuccessMessage(this.roomService.leaveRoom(userName, roomName));
    }


    @GetMapping("/api/poker/{roomName}/{userName}/delete-room")
    @ResponseBody
    public SuccessMessage deleteRoom(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/delete-room", roomName, userName);
        return new SuccessMessage(this.roomService.deleteRoom(userName, roomName));
    }


    @GetMapping("/api/poker/{roomName}/{userName}/create-room")
    @ResponseBody
    public SuccessMessage createRoom(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/create-room", roomName, userName);
        return new SuccessMessage(this.roomService.createRoom(userName, roomName));
    }

}
