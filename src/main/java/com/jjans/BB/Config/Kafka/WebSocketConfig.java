package com.jjans.BB.Config.Kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // `/chatting/queue/room/{room_id}'
        registry.enableSimpleBroker("/chatting/topic");
        registry.setApplicationDestinationPrefixes("/chatting/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws-chat")
                .setAllowedOrigins("*");
        registry.addEndpoint("/ws-chat").setAllowedOrigins("*").withSockJS();
    }


}