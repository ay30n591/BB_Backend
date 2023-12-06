package com.jjans.BB.Config.Kafka;


import com.jjans.BB.Dto.ChatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor

public class KafkaConsumer {

    private final SimpMessagingTemplate template;
    @KafkaListener(groupId = "my-consumer-group" ,topics="chatting")
    public void listenChat(ChatDto chatDto){
        template.convertAndSend("/chatting/topic/room/"+chatDto.getRoomId(), chatDto);
    }

}