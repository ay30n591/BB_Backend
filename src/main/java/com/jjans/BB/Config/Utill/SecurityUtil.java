package com.jjans.BB.Config.Utill;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static String getCurrentUserEmail() {
        // 현재 인증(Authentication) 정보를 가져옴
        logger.info("Current Authentication: {}", SecurityContextHolder.getContext().getAuthentication());

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Current Authentication: {}", authentication);

        // 인증 정보가 없거나 사용자 이름이 없다면 예외를 던짐
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        // 현재 사용자의 이메일을 반환
        return authentication.getName();
    }


}
