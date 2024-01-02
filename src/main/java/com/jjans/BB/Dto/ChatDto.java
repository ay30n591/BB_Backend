package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Chat;
import com.jjans.BB.Entity.ChatRoom;
import com.jjans.BB.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {

    private String message;
    private String sender;
    private Long roomId;
    private LocalDateTime createdTime;
    private int readCount; // 기본값 2로 설정


    public ChatDto(Chat chat) {
        this.message = chat.getMessage();
        this.sender = chat.getSender().getNickName();
        this.roomId = chat.getChatRoom().getId();
        this.readCount = chat.getReadCount();
        this.createdTime = chat.getCreateDate();
    }

    public Chat toEntity() {
        Chat chat = Chat.builder()
                .message(message)
                .build();
        return chat;
    }


}


