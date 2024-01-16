package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Users;
import com.jjans.BB.Entity.UsersDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponseDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private Long accessTokenExpirationTime;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }

    @Builder
    public UserResponseDto(Long userId, String userName) {
    }

    public static UserResponseDto from(UsersDocument usersDocument) {
        return UserResponseDto.builder()
                .userId(usersDocument.getId())
                .userName(usersDocument.getUserName())
                .build();
    }
}
