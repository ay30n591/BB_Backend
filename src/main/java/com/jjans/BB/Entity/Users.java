package com.jjans.BB.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Enum.AuthProvider;
import com.jjans.BB.Enum.Role;
import com.jjans.BB.Oauth2.OAuth2UserInfo;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email"}))
@JsonIgnoreProperties({"following", "followers", "bookmarkedPosts"})
public class Users extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = true)
    private String password = "";

    @Column(nullable = true)
    private  String userName;
    @Column(nullable = true)

    private  String nickName;
    @Column(nullable = true)

    private String userImgSrc;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = true)
    private LocalDateTime birth;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;
    public Users update(OAuth2UserInfo oAuth2UserInfo) {
        this.userName = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();
        return this;
    }

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleLike> likedArticles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMark> bookMarkedArticles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFollower> following;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFollower> follower;

    public void addRole(String roleName) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(roleName);
    }

}