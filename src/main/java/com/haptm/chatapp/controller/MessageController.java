package com.haptm.chatapp.controller;

import com.haptm.chatapp.model.Message;
import com.haptm.chatapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public Message sendMessage(Message message) {
        Message newMessage = messageService.saveMessage(message);
        String userId = String.valueOf(newMessage.getReceiver().getUserId());
        String destination = "/user/" + userId +"/queue/message";
        simpMessagingTemplate.convertAndSendToUser(userId, "/queue/message", newMessage);
        return newMessage;
    }
}
