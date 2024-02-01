package com.jjans.BB.Dto;

import com.jjans.BB.Entity.ChatRoom;
import com.jjans.BB.Entity.Users;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ChatRoomDto {
    private Long id;
    private Set<Long> participantIds;


    public ChatRoomDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.participantIds = chatRoom.getParticipants().stream()
                .map(Users::getId)
                .collect(Collectors.toSet());
    }
    public ChatRoom toEntity() {
        ChatRoom chatRoom = new ChatRoom();

        // participantIds가 주어진 경우, Users 엔터티로 변환하여 chatRoom에 추가
        if (participantIds != null && !participantIds.isEmpty()) {
            Set<Users> participants = participantIds.stream()
                    .map(userId -> {
                        Users user = new Users();
                        user.setId(userId);
                        return user;
                    })
                    .collect(Collectors.toSet());
            chatRoom.setParticipants(participants);
        }

        return chatRoom;
    }
}
