package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Users;
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
}
