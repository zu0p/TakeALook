/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.kurento.tutorial.groupcall;

import java.io.IOException;

import org.kurento.client.IceCandidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * 
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
@CrossOrigin("*")
public class CallHandler extends TextWebSocketHandler {

  private static final Logger log = LoggerFactory.getLogger(CallHandler.class);

  private static final Gson gson = new GsonBuilder().create();

  @Autowired
  private RoomManager roomManager;

  @Autowired
  private UserRegistry registry;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    final JsonObject jsonMessage = gson.fromJson(message.getPayload(), JsonObject.class);

    final UserSession user = registry.getBySession(session);

    if (user != null) {
      log.debug("Incoming message from user '{}': {}", user.getName(), jsonMessage);
    } else {
      log.debug("Incoming message from new user: {}", jsonMessage);
    }

    switch (jsonMessage.get("id").getAsString()) {
      case "joinRoom":
        joinRoom(jsonMessage, session);
        break;
      case "receiveVideoFrom":
        final String senderName = jsonMessage.get("sender").getAsString();
        final UserSession sender = registry.getByName(senderName);
        final String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
        user.receiveVideoFrom(sender, sdpOffer);
        break;
      case "leaveRoom":
        leaveRoom(user);
        break;
      case "onIceCandidate":
        JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();
        if (user != null) {
          IceCandidate cand = new IceCandidate(candidate.get("candidate").getAsString(),
              candidate.get("sdpMid").getAsString(), candidate.get("sdpMLineIndex").getAsInt());
          user.addCandidate(cand, jsonMessage.get("name").getAsString());
        }
        break;
      case "sendChatMessage":
        broadCastMessage(jsonMessage, session);
        break;
      case "proposePrice":
        realTimeProposal(jsonMessage, session);
        break;
      case "tradeClosed":
        tradeNowClose(jsonMessage, session);
        break;
      default:
        break;
    }
  }

  private void broadCastMessage(JsonObject params, WebSocketSession session) throws IOException{
    final String roomName = params.get("room").getAsString();
    final String name = params.get("name").getAsString();
    final String newMessage = params.get("message").getAsString();
    log.info("Message broadcasting {} {} {}",roomName, name, newMessage);

    Room room = roomManager.getRoom(roomName);
    room.broadCastMessages(name, newMessage);
  }
  // 실시간 가격 제안
  private void realTimeProposal(JsonObject params, WebSocketSession session) throws IOException{
    final String roomName = params.get("room").getAsString();
    final String name = params.get("name").getAsString();
    final int price = params.get("price").getAsInt();
    log.info("Message proposePrice {} {} {}",roomName, name, price);

    Room room = roomManager.getRoom(roomName);
    room.realTimeProposals(name, price);
  }
  // 최종 낙찰의 경우
  private void tradeNowClose(JsonObject params, WebSocketSession session) throws IOException{
    final String roomName = params.get("room").getAsString();
    log.info("Message tradeClosed {}", roomName);
    Room room = roomManager.getRoom(roomName);
    room.tradeNowClosed();
  }

  @Override // user session 제거
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    UserSession user = registry.removeBySession(session);
    roomManager.getRoom(user.getRoomName()).leave(user);
  }

  private void joinRoom(JsonObject params, WebSocketSession session) throws IOException {
    final String roomName = params.get("room").getAsString();
    final String name = params.get("name").getAsString();
    final int basePrice = params.get("basePrice").getAsInt();
    final String role = params.get("role").getAsString();
    log.info("PARTICIPANT {}: trying to join room {}, price is {}, role is {}", name, roomName, basePrice, role);

    Room room = roomManager.getRoom(roomName);
    final UserSession user = room.join(name, session, role, basePrice);
    registry.register(user);
  }

  private void leaveRoom(UserSession user) throws IOException {
    final Room room = roomManager.getRoom(user.getRoomName());
    room.leave(user);
    if (room.getParticipants().isEmpty()) {
      roomManager.removeRoom(room);
    }
  }
}
