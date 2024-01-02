package com.jjans.BB.Config.Kafka;

import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, String> chatRoomConnectTemplate;
    private final ChatService chatService;
    private final Logger logger = LoggerFactory.getLogger(StompHandler.class);
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String jwtToken = accessor.getFirstNativeHeader("Authorization");

        switch (accessor.getCommand()){
            case CONNECT:
                // jwtToken이 null 또는 빈 문자열이 아닌 경우에만 validateToken 호출
                if (jwtToken != null && !jwtToken.isEmpty()) {
                    tokenProvider.validateToken(jwtToken);

                } else {
                    logger.warn("JWT Token is null or empty");
                }
                break;
            case SUBSCRIBE:
                // userNickname 불러오기
                String destination = accessor.getDestination();
                int lastIndex = destination.lastIndexOf('/');
                String roomId = destination.substring(lastIndex + 1);

                String email = tokenProvider.extractUserEmail(jwtToken);
                logger.info("roomId is {}", roomId);
                String key = "chatroom:" + roomId;


                chatRoomConnectTemplate.opsForValue().set(key,tokenProvider.extractUserEmail(jwtToken));
                logger.info("webSocket lastIndex is {}", roomId);

               //  읽지 않은 채팅 읽음 처리
                chatService.decreaseReadCount(Long.valueOf(roomId), email);
//
//                Set<String> keys = chatRoomConnectTemplate.keys("chatroom:*");
//                for (String k : keys) {
//                    String value = chatRoomConnectTemplate.opsForValue().get(k);
//                    System.out.println("Key: " + k + ", Value: " + value);
//                }
                int size  = chatRoomConnectTemplate.keys(key).size();


        }



        return message;
    }



}
