package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
//    Page<Chat> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom, Pageable pageable);

    @Query("SELECT c FROM Chat c WHERE c.chatRoom.id = :roomId ORDER BY c.id DESC")
    Page<Chat> getChatMessagesByRoomId(@Param("roomId") Long roomId, Pageable pageable);

    @Query("SELECT c FROM Chat c WHERE c.chatRoom.id = :roomId")
    List<Chat> getChatByRoomId(@Param("roomId") Long roomId);

}