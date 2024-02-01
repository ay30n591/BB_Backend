package com.jjans.BB.Service;

import com.jjans.BB.Dto.ChatDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {
    ChatDto saveChatMessage(ChatDto chat, String email);
    List<ChatDto> getChatMessagesByRoomIdWithPaging(Long roomId, int page, int size);
    void decreaseReadCount(Long roomId, String email, int size);
    void updateMessage(Long roomId, String email, int size);
}
