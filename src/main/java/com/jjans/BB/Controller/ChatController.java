package com.jjans.BB.Controller;

import com.jjans.BB.Config.Kafka.KafkaProducer;
import com.jjans.BB.Dto.ChatDto;
import com.jjans.BB.Service.ChatRoomService;
import com.jjans.BB.Service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.validation.Valid;

@Controller
@Slf4j
public class ChatController {

    private ChatRoomService chatRoomService;
    private ChatService chatService;
    private final KafkaProducer producer;
    @Autowired
    public ChatController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping(value="/v1/message", consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody ChatDto chatDto) {
        if(!chatRoomService.existsRoom(chatDto.getRoomId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Room with ID " + chatDto.getRoomId() + " not found.");
        }
        ChatDto savedMessage = chatService.saveChatMessage(chatDto);
        producer.sendMessage(savedMessage);

        return ResponseEntity.ok(savedMessage);
    }

    @MessageMapping("/chatting/pub")
    public void sendSocketMessage(@RequestBody ChatDto chatDto) {
        if(!chatRoomService.existsRoom(chatDto.getRoomId())){
            return;
        }
        ChatDto savedMessage = chatService.saveChatMessage(chatDto);
        producer.sendMessage(savedMessage);

    }

}