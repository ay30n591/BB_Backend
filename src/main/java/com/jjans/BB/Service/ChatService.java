package com.jjans.BB.Service;

import com.jjans.BB.Dto.ChatDto;

import java.util.List;

public interface ChatService {
    ChatDto saveChatMessage(ChatDto chat);
//    List<ChatDto> getChatMessagesByRoomId(Long roomId, int page, int size);
}
