package com.ssafy.db.repository.chat;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.chat.ChatListGetRes;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.db.entity.Chat;
import com.ssafy.db.entity.QChat;
import com.ssafy.db.entity.QProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QChat qChat = QChat.chat;

    public List<ChatListGetRes> findChatByRoomId(String roomId){

        List<ChatListGetRes> chatList = jpaQueryFactory
                    .select(Projections.constructor(ChatListGetRes.class, qChat.writer, qChat.message))
                    .from(qChat)
                            .where(qChat.roomId.eq(roomId))
                            .orderBy(qChat.sendTime.asc())
                    .fetch();
            return chatList;
    }
}
