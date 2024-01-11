package com.jjans.BB.Dto;

import com.amazonaws.services.kms.model.MessageType;
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
    private Chat.ChatType chatType; // 메시지 타입 추가
    private String message;
    private String sender;
    private Long roomId;
    private LocalDateTime createdTime;
    private int readCount; // 기본값 2로 설정


    public ChatDto(Chat chat) {
        this.chatType = chat.getChatType();
        this.message = chat.getMessage();
        this.sender = chat.getSender().getNickName();
        this.roomId = chat.getChatRoom().getId();
        this.readCount = chat.getReadCount();
        this.createdTime = chat.getCreateDate();
    }

    public Chat toEntity() {
        Chat chat = Chat.builder()
                .chatType(chatType)
                .message(message)
                .build();
        return chat;
    }


}


