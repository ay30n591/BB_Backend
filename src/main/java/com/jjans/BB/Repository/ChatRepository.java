package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Chat;
import com.jjans.BB.Entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
//    Page<Chat> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom, Pageable pageable);
}