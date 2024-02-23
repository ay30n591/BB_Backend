package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.FollowDto;
import com.jjans.BB.Entity.UserFollower;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Service.FollowService;
import com.jjans.BB.Repository.UsersRepository;

import org.springframework.stereotype.Service;
@Service
public class FollowServiceImpl implements FollowService {

    private final UsersRepository usersRepository;

    public FollowServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public void followUser(String followerNickName) {
        String userEmail = SecurityUtil.getCurrentUserEmail();

        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + userEmail));

        Users follower = usersRepository.findByNickName(followerNickName)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found with email: " + followerNickName));

        if (!userIsFollowing(user, follower)) {
            UserFollower userFollower = new UserFollower();
            userFollower.setUser(user);
            userFollower.setFollower(follower);

            user.getFollowing().add(userFollower);

            usersRepository.save(user);
            usersRepository.save(follower);
        }
    }

    @Override
    public void unfollowUser(String followerNickName) {
        String userEmail = SecurityUtil.getCurrentUserEmail();

        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + userEmail));

        Users follower = usersRepository.findByNickName(followerNickName)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found with email: " + followerNickName));

        UserFollower userFollower = getUserFollower(user, follower);
        if (userFollower != null) {
            user.getFollowing().remove(userFollower);
            follower.getFollower().remove(userFollower);

            usersRepository.save(user);
            usersRepository.save(follower);
        }
    }

    @Override
    public FollowDto getFollowInfo() {
        String userEmail = SecurityUtil.getCurrentUserEmail();

        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + userEmail));

        return new FollowDto(user);
    }


    private boolean userIsFollowing(Users user, Users follower) {
        return user.getFollowing().stream()
                .anyMatch(uf -> uf.getFollower().equals(follower));
    }

    private UserFollower getUserFollower(Users user, Users follower) {
        return user.getFollowing().stream()
                .filter(uf -> uf.getFollower().equals(follower))
                .findFirst()
                .orElse(null);
    }

}
