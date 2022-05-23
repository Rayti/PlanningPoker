package com.example.planningpoker.controller.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class DummyWebSocketController {

    private SimpMessagingTemplate msgTemplate;

    @Autowired
    public DummyWebSocketController(SimpMessagingTemplate msgTemplate) {
        this.msgTemplate = msgTemplate;
    }

    @MessageMapping("/chat")
    @SendTo("/backend-response/messages")
    public Message getMessages(Message msg) {

        System.out.println(msg.getMessage());
        return msg;
    }

    @MessageMapping("/chat6")
    public Message getMessagesParameterised(Message msg) {
        System.out.println(msg.getMessage());
        msg.setMessage(msg.getMessage().concat(" (response from backend)"));

        msgTemplate.convertAndSend("/backend-response/messages/param", msg);

        return msg;
    }
}
