package com.example.planningpoker.controller.room;

import com.example.planningpoker.controller.SuccessMessage;
import com.example.planningpoker.controller.room.message.JoinedRoomMessage;
import com.example.planningpoker.controller.room.message.LeftRoomMessage;
import com.example.planningpoker.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@CrossOrigin
public class RoomController {

    public static final String BACKEND_SOCKET_RESPONSE_FORMAT = "/backend-socket-response/%s";
    private RoomService roomService;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RoomController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }


    @GetMapping("/api/poker/{roomName}/{userName}/join-room")
    @ResponseBody
    public SuccessMessage joinRoom(@PathVariable String roomName, @PathVariable String userName){
        log.info("/api/poker/{}/{}/join-room", roomName, userName);
        //DONE
        return new SuccessMessage(this.roomService.joinRoom(userName, roomName));
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/join-room-ws")
    public void joinRoomWebSocket(@DestinationVariable String roomName, @DestinationVariable String userName) {
        log.info("/api/poker/{}/{}/join-room-ws", roomName, userName);

        boolean success = this.roomService.joinRoom(userName, roomName);
        if (success) {
            JoinedRoomMessage msg = new JoinedRoomMessage("JoinedRoomMessage", userName);
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/join-room-ws SENT JoinedRoomMessage to subscribers", roomName, userName);
        }
    }

    @GetMapping("/api/poker/{roomName}/{userName}/can-join-room")
    public SuccessMessage canJoinRoom(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/can-join-room", roomName, userName);
        boolean success = roomService.canJoinRoom(userName, roomName);
        return new SuccessMessage(success);
    }


    @GetMapping("/api/poker/{roomName}/{userName}/leave-room")
    @ResponseBody
    public SuccessMessage leaveRoom(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/leave-room", roomName, userName);
        //DONE
        return new SuccessMessage(this.roomService.leaveRoom(userName, roomName));
    }

    @MessageMapping("/api/poker/{roomName}/{userName}/leave-room-ws")
    public void leaveRoomWebSocket(@DestinationVariable String roomName, @DestinationVariable String userName) {
        log.info("/api/poker/{}/{}/leave-room", roomName, userName);
        boolean success = this.roomService.leaveRoom(userName, roomName);
        if (success) {
            LeftRoomMessage msg = new LeftRoomMessage("LeftRoomMessage", userName);
            messagingTemplate.convertAndSend(String.format(BACKEND_SOCKET_RESPONSE_FORMAT, roomName), msg);
            log.info("/api/poker/{}/{}/leave-room SENT LeftRoomMessage to subscribers", roomName, userName);
        }
    }


    @GetMapping("/api/poker/{roomName}/{userName}/delete-room")
    @ResponseBody
    public SuccessMessage deleteRoom(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/delete-room", roomName, userName);
        //DONE
        return new SuccessMessage(this.roomService.deleteRoom(userName, roomName));
    }


    @GetMapping("/api/poker/{roomName}/{userName}/create-room")
    @ResponseBody
    public SuccessMessage createRoom(@PathVariable String roomName, @PathVariable String userName) {
        log.info("/api/poker/{}/{}/create-room", roomName, userName);
        //DONE
        return new SuccessMessage(this.roomService.createRoom(userName, roomName));
    }

}
