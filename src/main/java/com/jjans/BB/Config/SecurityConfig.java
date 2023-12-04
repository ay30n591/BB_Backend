package com.jjans.BB.Config;

import com.jjans.BB.Config.Security.JwtAuthenticationFilter;
import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Oauth2.OAuth2AuthenticationFailureHandler;
import com.jjans.BB.Oauth2.OAuth2AuthenticationSuccessHandler;
import com.jjans.BB.Repository.CookieAuthorizationRequestRepository;
import com.jjans.BB.Service.AuthService;
import com.jjans.BB.Service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)

public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final RedisTemplate redisTemplate;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //요청에 대한 권한 설정
        http.authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui").permitAll()
                .antMatchers("/api/v1/users/sign-up", "/api/v1/users/login", "/api/v1/users/authority",
                        "https://localhost:8080/**","/api/v1/users/reissue", "/api/v1/users/logout").permitAll()
                .antMatchers("/api/v1/users/userTest").hasRole("USER")
                .antMatchers("/api/v1/users/adminTest").hasRole("ADMIN")
                .anyRequest().authenticated();

//                        //oauth2Login
//        http.oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorization")  // 소셜 로그인 url
//                .authorizationRequestRepository(cookieAuthorizationRequestRepository)  // 인증 요청을 cookie 에 저장
//
//                .and()
//                //userService()는 OAuth2 인증 과정에서 Authentication 생성에 필요한 OAuth2User 를 반환하는 클래스를 지정한다.
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
//                // 회원 정보 처리
//
//                .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler)
//                .failureHandler(oAuth2AuthenticationFailureHandler);
//
//        http.logout()
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID");

        //jwt filter 설정
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthService authService() {
        return new AuthService();
    }
}