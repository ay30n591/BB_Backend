package com.jjans.BB.Config.Kafka;

// KafkaProducer.java

import com.jjans.BB.Dto.ChatDto;
import com.jjans.BB.Dto.ChatRoomDto;
import com.jjans.BB.Entity.Chat;
import com.jjans.BB.Service.ChatRoomService;
import com.jjans.BB.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {


    private static final String TOPIC = "chatting";
    private final KafkaTemplate<String, ChatDto> kafkaTemplate;
    private ChatService chatService;
    private ChatRoomService chatRoomService;


    public void sendMessage(ChatDto message) {

        ListenableFuture<SendResult<String, ChatDto>> listenable = kafkaTemplate.send(TOPIC, message);
        listenable.addCallback(new ListenableFutureCallback<SendResult<String, ChatDto>>() {
            @Override
            public void onSuccess(SendResult<String, ChatDto> result) {
                log.info("Successfully sent message: {}", message);
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to send message: {}", message, ex);
            }
        });

        log.info("Produce message: {}", message);

    }
}