package com.jjans.BB.Config.Kafka;

import com.jjans.BB.Dto.ChatDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    //@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer = "kafka:9092";
    @Value("${spring.kafka.consumer.key-deserializer}")
    private String deserializerKey;
    @Value("${spring.kafka.consumer.value-deserializer}")
    private String deserializerValue;

    @Bean
    public ConsumerFactory<String, ChatDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigurations(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ChatDto.class))
        );
    }

    private Map<String, Object> consumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configurations.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest"); // earliest: 전체 , latest: 최신 메시지
        return configurations;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatDto> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, ChatDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}