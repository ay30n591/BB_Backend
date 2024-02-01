package com.jjans.BB.Service;

import com.jjans.BB.Dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomDto> getAllChatRoomsByUser();
    ChatRoomDto createChatRoom(String nickname);
    boolean existsRoom(Long roomId);

}
