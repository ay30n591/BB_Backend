package com.jjans.BB.Entity;

import com.jjans.BB.Enum.AuthProvider;
import com.jjans.BB.Oauth2.OAuth2UserInfo;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email"}))
public class Users extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "email")
    private String email;

//    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private  String userName;

//    @Column(nullable = false, unique = true)
    private  String nickName;

    private int picture;

    private String gender;

    private String birth;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    public Users update(OAuth2UserInfo oAuth2UserInfo) {
        this.userName = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();
        return this;
    }

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
//    @Enumerated(EnumType.STRING)
    private List<String> roles = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    public void addRole(String roleName){
        this.roles = new ArrayList<>(List.copyOf(this.roles));
        this.roles.add(roleName);
    }

}