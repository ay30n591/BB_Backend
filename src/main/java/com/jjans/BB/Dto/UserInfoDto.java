package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Playlist;
import com.jjans.BB.Entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoDto {

    private long id;
    private String email;
    private  String userName;
    private  String nickName;
    private String imgSrc;
    private String gender;
    private String birth;

    private int followingCnt;
    private int followerCnt;

    public UserInfoDto(Users users) {
        this.id = users.getId();
        this.email = users.getEmail();
        this.userName = users.getUserName();
        this.nickName = users.getNickName();
        this.imgSrc = users.getImgSrc();
        this.gender = users.getGender();
        this.birth = users.getBirth();
        this.followingCnt = users.getFollowing().size();
        this.followerCnt = users.getFollowers().size();

    }

    }
