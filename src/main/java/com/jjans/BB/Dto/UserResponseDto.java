package com.jjans.BB.Dto;

//import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        private String userName;
        private String nickName;
        private String imgSrc;

        public TokenInfo withUserInfo(Users user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.userName = user.getUserName();
            this.nickName = user.getNickName();
            this.imgSrc = user.getUserImgSrc();
            return this;
        }

    }


    @NoArgsConstructor
    @Getter
    public static class UserInfoDto {

        private long id;
        private String email;
        private  String userName;
        private  String nickName;
        private String userImgSrc;
        private String gender;
        private LocalDate birth;
        private int followingCnt;
        private int followerCnt;

        public UserInfoDto(Users users) {
            this.id = users.getId();
            this.email = users.getEmail();
            this.userName = users.getUserName();
            this.nickName = users.getNickName();
            this.userImgSrc = users.getUserImgSrc();
            this.gender = users.getGender();
            this.birth = users.getBirth();
            this.followingCnt = users.getFollowing().size();
            this.followerCnt = users.getFollower().size();
        }

    }
}
