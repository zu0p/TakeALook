package com.ssafy.api.response.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatListGetRes {
    private String writer;
    private String message;

    public ChatListGetRes(String writer, String message) {
        this.writer = writer;
        this.message = message;
    }
}
