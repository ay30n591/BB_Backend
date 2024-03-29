package com.jjans.BB.Controller;

import com.jjans.BB.Config.Kafka.KafkaProducer;
import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Dto.ChatDto;
import com.jjans.BB.Dto.ChatRoomDto;
import com.jjans.BB.Entity.Chat;
import com.jjans.BB.Service.ChatRoomService;
import com.jjans.BB.Service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class ChatController {

    private ChatRoomService chatRoomService;
    private ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> chatRoomConnectTemplate;

    private final KafkaProducer producer;
    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    public ChatController(ChatRoomService chatRoomService, ChatService chatService, JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> chatRoomConnectTemplate, KafkaProducer producer) {
        this.chatRoomService = chatRoomService;
        this.chatService = chatService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.chatRoomConnectTemplate = chatRoomConnectTemplate;
        this.producer = producer;
    }

    //@DestinationVariable String chatRoomID,
    @MessageMapping("/message")
    public void sendSocketMessage(ChatDto chatDto,
                                  @Header("Authorization") String Authorization) {

        logger.info("sendSocketMessage called with Authorization: {}", Authorization);

        if(!chatRoomService.existsRoom(chatDto.getRoomId())){
            return;
        }
        String email = jwtTokenProvider.extractUserEmail(Authorization);
        String key = "chatroom:" + chatDto.getRoomId().toString();
        int size = chatRoomConnectTemplate.opsForSet().members(key).size();
        chatDto.setChatType(Chat.ChatType.MESSAGE);

        if (size > 1){
            chatDto.setReadCount(0);
        }
        else {
            chatDto.setReadCount(1);
        }

        producer.sendMessage(chatDto);
        chatService.saveChatMessage(chatDto,email);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/rooms")
    @Operation(summary = "채팅방 목록 가져오기", description = "채팅방 목록 가져오기")
    public List<ChatRoomDto> getAllChatRoomsByUser() {
        return chatRoomService.getAllChatRoomsByUser();
    }


    @GetMapping("/room/{roomId}/messages")
    @Operation(summary = "채팅 메세지 가져오기", description = "채팅 메세지 가져오기")

    public List<ChatDto> getChatMessagesByRoomIdWithPaging(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return chatService.getChatMessagesByRoomIdWithPaging(roomId, page, size);
    }


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/create-room")
    @Operation(summary = "채팅방 생성", description = "채팅방 생성")

    public ChatRoomDto createChatRoom(@RequestParam String nickname) {
        return chatRoomService.createChatRoom(nickname);
    }

}