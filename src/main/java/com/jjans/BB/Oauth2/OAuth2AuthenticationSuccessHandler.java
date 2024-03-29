package com.jjans.BB.Oauth2;

import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Config.Utill.CookieUtils;
import com.jjans.BB.Dto.UserResponseDto;
import com.jjans.BB.Repository.CookieAuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.jjans.BB.Repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://3.37.110.13:3000"}) // CORS 설정
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//   private String "redirectUri";
   private final JwtTokenProvider jwtTokenProvider;
   private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
      String targetUrl = determineTargetUrl(request, response, authentication);

      if (response.isCommitted()) {
         log.debug("Response has already been committed.");
         return;
      }
      clearAuthenticationAttributes(request, response);
      getRedirectStrategy().sendRedirect(request, response, targetUrl);
   }

   protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
      Optional<String> redirectUri = CookieUtils.getCookie(request,REDIRECT_URI_PARAM_COOKIE_NAME)
              .map(Cookie::getValue);

      if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
         throw new RuntimeException("redirect URIs are not matched.");
      }
      String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

      //JWT 생성
      UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

      return UriComponentsBuilder.fromUriString(targetUrl)
              .queryParam("token", tokenInfo.getAccessToken())
              .build().toUriString();
   }

   protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
      super.clearAuthenticationAttributes(request);
      cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
   }

   private boolean isAuthorizedRedirectUri(String uri) {
      URI clientRedirectUri = URI.create(uri);
      URI authorizedUri = URI.create("http://localhost:8080/oauth2/callback/{provider}");

      if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
              && authorizedUri.getPort() == clientRedirectUri.getPort()) {
         return true;
      }
      return false;
   }
}
