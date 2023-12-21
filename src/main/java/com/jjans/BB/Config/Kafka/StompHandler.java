package com.jjans.BB.Config.Kafka;

import com.jjans.BB.Config.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider tokenProvider;
    private final Logger logger = LoggerFactory.getLogger(StompHandler.class);
    private static final ThreadLocal<String> currentUserEmailThreadLocal = new InheritableThreadLocal<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()){
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            logger.info("webSocket token is {}", jwtToken);

            // jwtToken이 null 또는 빈 문자열이 아닌 경우에만 validateToken 호출
            if (jwtToken != null && !jwtToken.isEmpty()) {

                tokenProvider.validateToken(jwtToken);
                String userEmail = tokenProvider.extractUserEmail(jwtToken);
                logger.info("User email extracted from token: {}", userEmail);
                currentUserEmailThreadLocal.set(userEmail);

            } else {
                logger.warn("JWT Token is null or empty");
            }
        }

         return message;
    }

    public static String getCurrentUserEmail() {
        return currentUserEmailThreadLocal.get();
    }
}
