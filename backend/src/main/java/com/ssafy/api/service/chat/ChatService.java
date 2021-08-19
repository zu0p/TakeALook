package com.ssafy.api.service.chat;

import com.ssafy.api.request.chat.ChatMessage;
import com.ssafy.api.response.chat.ChatListGetRes;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface ChatService {
    void saveChat(String chatList) throws ParseException;

    void saveMessage(ChatMessage message);

    List<ChatListGetRes> getChatList(String roomId);
}
