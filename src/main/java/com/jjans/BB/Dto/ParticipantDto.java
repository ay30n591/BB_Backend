package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Users;
import lombok.Getter;

@Getter
public class ParticipantDto {
    private Long participantId;
    private String participantName;
    private String participantImgSrc;

    public ParticipantDto(Users user) {
        this.participantId = user.getId();
        this.participantName = user.getNickName();
        this.participantImgSrc = user.getUserImgSrc();
    }
}
