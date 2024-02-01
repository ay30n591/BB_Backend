package com.jjans.BB.Repository;

import com.jjans.BB.Entity.ChatRoom;
import com.jjans.BB.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByParticipantsContaining(Users user);
}