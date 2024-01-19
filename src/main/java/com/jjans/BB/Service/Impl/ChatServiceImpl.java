package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Kafka.KafkaProducer;
import com.jjans.BB.Config.Security.JwtTokenProvider;
import com.jjans.BB.Dto.ChatDto;
import com.jjans.BB.Entity.Chat;
import com.jjans.BB.Entity.ChatRoom;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.ChatRepository;
import com.jjans.BB.Repository.ChatRoomRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UsersRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> chatRoomConnectTemplate;
    private final KafkaProducer producer;
    private final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    public ChatServiceImpl(ChatRepository chatRepository, UsersRepository userRepository, ChatRoomRepository chatRoomRepository, JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> chatRoomConnectTemplate, KafkaProducer producer) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.chatRoomConnectTemplate = chatRoomConnectTemplate;
        this.producer = producer;
    }


    @Override
    public ChatDto saveChatMessage(ChatDto chatDto, String email) {
            ChatRoom room = chatRoomRepository.findById(chatDto.getRoomId())
                    .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

            Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

            Chat chat = chatDto.toEntity();
            chat.setChatRoom(room);
            chat.setSender(user);
            chat.setChatType(chatDto.getChatType());
            chat.setReadCount(chatDto.getReadCount());

            Chat savedChat = chatRepository.save(chat);

        return new ChatDto(savedChat);
    }

    @Override
    public List<ChatDto> getChatMessagesByRoomIdWithPaging(Long roomId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        // 페이징된 결과를 반환
        Page<Chat> chatMessagesPage = chatRepository.getChatMessagesByRoomId(roomId, pageable);
        List<ChatDto> chatDtoList = chatMessagesPage.getContent().stream()
                .sorted(Comparator.comparing(Chat::getId)) // id 기준으로 정방향으로 정렬
                .map(chat -> new ChatDto(
                        chat.getChatType(),
                        chat.getMessage(),
                        chat.getSender().getNickName(),
                        chat.getChatRoom().getId(),
                        chat.getCreateDate(),
                        chat.getReadCount()))
                .collect(Collectors.toList());

        return chatDtoList;
    }

    @Override
    public void decreaseReadCount(Long roomId, String email, int size) {
        List<Chat> chats = chatRepository.getChatByRoomId(roomId);
        // 내가 쓰지 않은 chat에 대해서만 읽음 처리.
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        if (size > 1) {
            for (Chat chat : chats) {
                if (!(user.getNickName() == chat.getSender().getNickName()))
                    if (chat != null) {
                        chat.setReadCount(0);
                        chatRepository.save(chat);
                    }
            }
        }


    }

    @Override
    public void updateMessage(Long roomId, String email, int size) {
        ChatDto chat = new ChatDto();
        chat.setChatType(Chat.ChatType.ENTER);
        chat.setMessage(email+"님 입장");
        chat.setSender("SysTem");
        if (size > 1){
            chat.setReadCount(0);
        }
        else {
            chat.setReadCount(1);
        }
        chat.setRoomId(roomId);
        producer.sendMessage(chat);
    }


}
