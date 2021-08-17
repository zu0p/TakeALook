package com.ssafy.db.repository.chat;

import com.ssafy.db.entity.Chat;
import org.kurento.client.internal.server.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Modifying
    @Query("DELETE FROM Chat c WHERE c.roomId = :roomId")
    void deleteAllByRoomId(@Param("roomId") String roomId);
}
