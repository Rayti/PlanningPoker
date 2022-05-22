package com.example.planningpoker.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DummyWebSocketController {


    @MessageMapping("/chat")
    @SendTo("/backend-response/messages")
    public Message getMessages(Message msg){

        System.out.println(msg.getMessage());
        return msg;
    }
}
