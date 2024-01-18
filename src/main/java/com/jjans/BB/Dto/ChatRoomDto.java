package com.jjans.BB.Dto;

import com.jjans.BB.Entity.ChatRoom;
import lombok.Getter;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Getter
public class ChatRoomDto {
    private Long id;
    private Set<ParticipantDto> participants;


    public ChatRoomDto(ChatRoom chatRoom) {
        System.out.println("Debugging: ChatRoomDto constructor");
        this.id = chatRoom.getId();
        this.participants = new TreeSet<>(Comparator.comparingLong(ParticipantDto::getParticipantId));

        if (chatRoom.getParticipants() != null) {
            chatRoom.getParticipants().stream()
                    .map(ParticipantDto::new)
                    .forEach(participants::add);
        } else {
            System.out.println("Debugging: Participants list is null");
        }
    }

}

