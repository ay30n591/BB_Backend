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
        private long id;
        private String email;
        private  String userName;
        private  String nickName;
        private String imgSrc;
        public TokenInfo withUserInfo(Users user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.userName = user.getUserName();
            this.nickName = user.getNickName();
            this.imgSrc = user.getImgSrc();
            return this;
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class searchInfo {
//        private long id;
        private String email;
        private  String userName;
        private  String nickName;
        private String imgSrc;
        public searchInfo searchUserInfo(UsersDocument usersDocument ) {
//            this.id = usersDocument.getId();
            this.email = usersDocument.getEmail();
            this.userName = usersDocument.getUserName();
            this.nickName = usersDocument.getNickName();
            this.imgSrc = usersDocument.getImgSrc();
            return this;
        }
    }
    @Builder
    public UserResponseDto(Long userId, String userName, String email, String nickName ) {
    }

    public static UserResponseDto from(UsersDocument usersDocument) {
        return UserResponseDto.builder()
                .userId(usersDocument.getId())
                .userName(usersDocument.getUserName())
                .nickName(usersDocument.getNickName())
                .email(usersDocument.getEmail())
                .build();
    }
}
