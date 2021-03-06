package com.ssafy.api.controller;

import com.ssafy.api.request.chat.ChatMessage;
import com.ssafy.api.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StompWebSockController {
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    @Autowired
    ChatService chatService;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage message){
        chatService.saveMessage(message);
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}