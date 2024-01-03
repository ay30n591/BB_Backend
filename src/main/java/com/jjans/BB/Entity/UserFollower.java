package com.jjans.BB.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_followers")
public class UserFollower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Users follower;

    // 내가 팔로잉 한 사람
    public String getFollowingNickname() {
        return follower.getNickName();
    }
    // 나의 팔로워
    public String getFollowerNickname() {
        return user.getNickName();
    }

}
