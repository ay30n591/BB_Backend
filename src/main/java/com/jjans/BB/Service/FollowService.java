package com.jjans.BB.Service;

import com.jjans.BB.Dto.FollowDto;
import com.jjans.BB.Entity.UserFollower;

import java.util.Set;

public interface FollowService {
    void followUser(String followerEmail);
    void unfollowUser(String followerEmail);
    FollowDto getFollowInfo();
}
