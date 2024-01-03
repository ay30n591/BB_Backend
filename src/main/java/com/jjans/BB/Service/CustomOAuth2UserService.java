package com.jjans.BB.Service;

import com.jjans.BB.Entity.Users;
import com.jjans.BB.Enum.AuthProvider;
import com.jjans.BB.Enum.Role;
import com.jjans.BB.Oauth2.OAuth2UserInfo;
import com.jjans.BB.Oauth2.OAuth2UserInfoFactory;
import com.jjans.BB.Oauth2.UserPrincipal;
import com.jjans.BB.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.RedirectView;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService  implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UsersRepository usersRepository;
    private final Logger logger = Logger.getLogger(CustomOAuth2UserService.class.getName());

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException{
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
        logger.info("Type - " + oAuth2User.getClass() + ",OAuth2User information: " + oAuth2User);
        System.out.println("OAuth2User information: " + oAuth2User);
        System.out.println("OAuth2User type: " + oAuth2User.getClass().getName());

        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }
    protected OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        //OAuth2 로그인 플랫폼 구분

        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        logger.info("AuthProvider: " + authProvider);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());
        logger.info( " Name: " + oAuth2UserInfo.getName() + oAuth2UserInfo.getEmail());

        if (!StringUtils.hasText(oAuth2UserInfo.getName())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }

        Users user = usersRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
        //이미 가입된 경우
        if (user != null) {
            if (!user.getAuthProvider().equals(authProvider)) {
                throw new RuntimeException("Email already signed up.");
            }
            user = updateUser(user, oAuth2UserInfo);

        }
        //가입되지 않은 경우
        else {
            user = registerUser(authProvider, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2UserInfo.getAttributes());
    }

    private Users registerUser(AuthProvider authProvider, OAuth2UserInfo oAuth2UserInfo) {

        Users users = Users.builder()
                .email(oAuth2UserInfo.getEmail())
                .nickName(" ")
                .oauth2Id(oAuth2UserInfo.getOAuth2Id())
                .authProvider(authProvider)
//                .authProvider(authProvider.GOOGLE) // 로컬 회원가입인 경우
                .role(Role.ROLE_USER)
                .build();

        return usersRepository.save(users);
    }


    private Users updateUser(Users user, OAuth2UserInfo oAuth2UserInfo) {
        // OAuth2UserInfo를 기반으로 사용자 정보 업데이트
        user = user.update(oAuth2UserInfo);

        // 사용자의 이름을 구글에서 제공한 값으로 userName 및 nickName으로 설정
        user.setUserName(oAuth2UserInfo.getName());
        user.setNickName(oAuth2UserInfo.getName());

        return usersRepository.save(user);
    }
}