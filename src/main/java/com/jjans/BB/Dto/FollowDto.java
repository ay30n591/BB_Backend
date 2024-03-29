package com.jjans.BB.Dto;

import com.jjans.BB.Entity.UserFollower;
import com.jjans.BB.Entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.stream.Collectors;
@NoArgsConstructor
@Getter
public class FollowDto {
    int followerSize;
    int followingSize;
    private Set<String> FollowNickNames;
    private Set<String> FollowingNickNames;
    private Set<String> FollowingImgs;
    private Set<String> FollowImgs;


    public FollowDto(Users user) {
        this.followerSize = user.getFollower().size();
        this.followingSize = user.getFollowing().size();
        this.FollowingNickNames = user.getFollowing().stream()
                .map(UserFollower::getFollowingNickname)
                .collect(Collectors.toSet());
        this.FollowNickNames = user.getFollower().stream()
                .map(UserFollower::getFollowerNickname)
                .collect(Collectors.toSet());
        this.FollowingImgs = user.getFollowing().stream()
                .map(UserFollower::getFollowingImgs)
                .collect(Collectors.toSet());
        this.FollowImgs = user.getFollower().stream()
                .map(UserFollower::getFollowingImgs)
                .collect(Collectors.toSet());
    }

}

