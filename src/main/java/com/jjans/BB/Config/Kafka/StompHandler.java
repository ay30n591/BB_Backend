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

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, String> chatRoomConnectTemplate;
    private final ChatService chatService;
    private final KafkaProducer producer;

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

                String destination = accessor.getDestination();
                int lastIndex = destination.lastIndexOf('/');
                String roomId = destination.substring(lastIndex + 1);

                String email = tokenProvider.extractUserEmail(jwtToken);
                String key = "chatroom:" + roomId;
                logger.info("webSocket lastIndex is {}", roomId);


                chatRoomConnectTemplate.opsForSet().add(key, email);
                Set<String> users = chatRoomConnectTemplate.opsForSet().members(key);
                for (String user : users) {
                    System.out.println("User: " + user);
                }
                int size  = users.size();
                logger.info("SUBSCRIBE size is {}", size);

               //  읽지 않은 채팅 읽음 처리
                chatService.decreaseReadCount(Long.valueOf(roomId), email);

                // 변경내용 상대방 화면에 -> 새로운 메세지를 보내. 해당 메세지를 받으면 메세지를 다시 불러와.
                break;
            case UNSUBSCRIBE:
                destination = accessor.getDestination();
                lastIndex = destination.lastIndexOf('/');
                roomId = destination.substring(lastIndex + 1);
                key = "chatroom:" + roomId;
                email = tokenProvider.extractUserEmail(jwtToken);
                chatRoomConnectTemplate.opsForSet().remove(key, email);
                users = chatRoomConnectTemplate.opsForSet().members(key);
                size  = users.size();
                logger.info("UNSUBSCRIBE. size is {}", size);
                break;
//                chatRoomConnectTemplate.opsForValue().getOperations().delete(key, email);

//                if (size > 1){
//                    List<ChatDto> chatMessages =  chatService.getChatMessagesByRoomIdWithPaging(Long.valueOf(roomId), 1, size);
//                    logger.info("chatMessages is {}", chatMessages);
//
//                    for (ChatDto chatMessage : chatMessages) {
//
//                        producer.sendMessage(chatMessage);
//                    }
                }
//                else {

//                    List<ChatDto> chatMessages =  chatService.getChatMessagesByRoomIdWithPaging(Long.valueOf(roomId), 1, size);
//                    logger.info("chatMessages is {}", chatMessages);
//
//                    for (ChatDto chatMessage : chatMessages) {
//                        producer.sendMessage(chatMessage);
//                    }
                //}
     //   }



        return message;
    }



}
