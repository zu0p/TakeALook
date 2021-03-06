package com.ssafy.api.controller;

import com.ssafy.api.response.chat.ChatListGetRes;
import com.ssafy.api.service.chat.ChatService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="채팅 API", tags = {"Chat."})
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    @PostMapping()
    @ApiOperation(value="채팅 저장", notes = "채팅 목록을 저장한다.")
    public ResponseEntity<?> saveChat(@RequestBody String chatList) throws ParseException {
        chatService.saveChat(chatList);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
    }

    @GetMapping("/{roomId}")
    @ApiOperation(value="채팅 조회", notes = "채팅 목록을 조회한다.")
    public ResponseEntity<?> getChatList(@PathVariable String roomId){
        List<ChatListGetRes> chatList = chatService.getChatList(roomId);
        if(chatList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(chatList);
    }
}
