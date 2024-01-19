package com.jjans.BB.Config.Kafka;

import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.UsersRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, String> chatRoomConnectTemplate;
    private final ChatService chatService;
    private final UsersRepository usersRepository;


    private final Logger logger = LoggerFactory.getLogger(StompHandler.class);
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String jwtToken = accessor.getFirstNativeHeader("Authorization");

        switch (accessor.getCommand()) {
            case CONNECT:
                // jwtToken이 null 또는 빈 문자열이 아닌 경우에만 validateToken 호출
                if (jwtToken != null && !jwtToken.isEmpty()) {
                    tokenProvider.validateToken(jwtToken);
                } else {
                    logger.warn("JWT Token is null or empty");
                }
                break;

            case SUBSCRIBE:

                String destination = accessor.getDestination();
                int lastIndex = destination.lastIndexOf('/');
                String roomId = destination.substring(lastIndex + 1);
                String email = tokenProvider.extractUserEmail(jwtToken);
                String key = "chatroom:" + roomId;

                logger.info("Room ID is {}", roomId);

                chatRoomConnectTemplate.opsForSet().add(key, email);
                Set<String> users = chatRoomConnectTemplate.opsForSet().members(key);
                logger.info("SUBSCRIBE size is {}", users.size());

                Users user = usersRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

                //  읽지 않은 채팅 읽음 처리
                chatService.decreaseReadCount(Long.valueOf(roomId), email, users.size());

                // 입장 메세지 전송
                chatService.updateMessage(Long.valueOf(roomId), user.getNickName(), users.size());
                break;

            case UNSUBSCRIBE:

                String subscriptionId = accessor.getSubscriptionId();
                lastIndex = subscriptionId.lastIndexOf('/');
                roomId = subscriptionId.substring(lastIndex + 1);
                key = "chatroom:" + roomId;
                email = tokenProvider.extractUserEmail(jwtToken);
                logger.info("Room ID is {}", roomId);


                // 채팅방 현재 인원 최신화.
                chatRoomConnectTemplate.opsForSet().remove(key, email);
                users = chatRoomConnectTemplate.opsForSet().members(key);
                logger.info("UNSUBSCRIBE. size is {}", users.size());
                break;

        }

        return message;
    }
}
