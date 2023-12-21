package com.jjans.BB.Controller;

import com.jjans.BB.Config.Kafka.KafkaProducer;
import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Dto.ChatDto;
import com.jjans.BB.Dto.ChatRoomDto;
import com.jjans.BB.Service.ChatRoomService;
import com.jjans.BB.Service.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ChatController {

    private ChatRoomService chatRoomService;
    private ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;

    private final KafkaProducer producer;
    @Autowired
    public ChatController(ChatRoomService chatRoomService, ChatService chatService, JwtTokenProvider jwtTokenProvider, KafkaProducer producer) {
        this.chatRoomService = chatRoomService;
        this.chatService = chatService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.producer = producer;
    }

    @MessageMapping("/message")
    public void sendSocketMessage(ChatDto chatDto, @Header("Authorization") String Authorization) {
        if(!chatRoomService.existsRoom(chatDto.getRoomId())){
            return;
        }
        String email = jwtTokenProvider.extractUserEmail(Authorization);
        ChatDto savedMessage = chatService.saveChatMessage(chatDto,email);
        producer.sendMessage(savedMessage);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/rooms")
    public List<ChatRoomDto> getAllChatRoomsByUser() {
        return chatRoomService.getAllChatRoomsByUser();
    }


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/create-room")
    public ChatRoomDto createChatRoom(@RequestParam String nickname) {
        return chatRoomService.createChatRoom(nickname);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/room/exists")
    public boolean existsRoom(@RequestParam Long roomId) {
        return chatRoomService.existsRoom(roomId);
    }

}