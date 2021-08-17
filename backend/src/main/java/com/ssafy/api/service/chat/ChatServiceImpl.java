package com.ssafy.api.service.chat;

import com.ssafy.api.response.chat.ChatListGetRes;
import com.ssafy.db.entity.Chat;
import com.ssafy.db.repository.chat.ChatRepositorySupport;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import com.ssafy.db.repository.chat.ChatRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("chatService")
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatRepositorySupport chatRepositorySupport;

    @Override
    public void saveChat(String chatList)  {
        try {
            JSONParser jsonParse = new JSONParser();
            JSONObject jObject = (JSONObject) jsonParse.parse(chatList);
            JSONArray dataList = (JSONArray) jObject.get("chatList");

            for (int i = 0; i < dataList.size(); i++) {
                Chat chat = new Chat();
                JSONObject data = (JSONObject) dataList.get(i);

                String message = (String) data.get("message");
                String writer = (String) data.get("writer");
                String roomId = (String) data.get("roomId");
                String sendTime = (String) data.get("sendTime");

                chat.setMessage(message);
                chat.setRoomId(roomId);
                chat.setSendTime(sendTime);
                chat.setWriter(writer);

                chatRepository.save(chat);
            }
        } catch(ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<ChatListGetRes> getChatList(String roomId){
        List<ChatListGetRes> chatList = chatRepositorySupport.findChatByRoomId(roomId);
        return chatList;
    }
}
