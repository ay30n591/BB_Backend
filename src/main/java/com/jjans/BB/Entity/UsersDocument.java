package com.jjans.BB.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Enum.AuthProvider;
import com.jjans.BB.Enum.Role;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
//        (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "users")
@Mapping(mappingPath = "elastic/users-mapping.json") //타입 매핑
@Setting(settingPath = "elastic/users-setting.json") //분석기 매핑
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email"}))
@JsonIgnoreProperties({"following", "followers", "bookmarkedPosts"})
public class UsersDocument {

    @Id
    private long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "email")
//    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String password = "";

    @Column(nullable = true)
    private  String userName;
    @Column(nullable = true)
//    @Column(nullable = false, unique = true)
    private  String nickName;
    @Column(nullable = true)

    private String imgSrc;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = true)
    private String birth;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

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
    private Set<UserFollower> followers;
//    public static Users from (UserRequestDto.RequestUserSaveDto requestUserSaveDto){
//        return Users.builder()
//                .userName(requestUserSaveDto.getUserName())
//                .nickName(requestUserSaveDto.getNickName())
//                .build();
//    }

    public static UsersDocument of(UserRequestDto.RequestUserSaveDto requestUserSaveDto) {
        UsersDocument usersDocument = new UsersDocument();
        usersDocument.setEmail(requestUserSaveDto.getEmail());
        usersDocument.setUserName(requestUserSaveDto.getUserName());
        usersDocument.setNickName(requestUserSaveDto.getNickName());
        // 나머지 필드들도 필요에 따라 설정하세요

        // roles를 초기화
        usersDocument.setRoles(new ArrayList<>());

        return usersDocument;    }
}