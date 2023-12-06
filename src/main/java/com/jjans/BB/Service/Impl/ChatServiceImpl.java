package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.ChatDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Entity.Chat;
import com.jjans.BB.Entity.ChatRoom;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.ChatRepository;
import com.jjans.BB.Repository.ChatRoomRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.ChatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UsersRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    public ChatServiceImpl(ChatRepository chatRepository, UsersRepository userRepository, ChatRoomRepository chatRoomRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }


    @Override
    public ChatDto saveChatMessage(ChatDto chatDto) {
            ChatRoom room = chatRoomRepository.findById(chatDto.getRoomId())
                    .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

            String userEmail = SecurityUtil.getCurrentUserEmail();
            Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

            Chat chat = chatDto.toEntity();
            chat.setChatRoom(room);
            chat.setSender(user);

            Chat savedChat = chatRepository.save(chat);

        return new ChatDto(savedChat);
    }

//    @Override
//    public List<ChatDto> getChatMessagesByRoomId(Long roomId, int page, int size) {
//        ChatRoom room = chatRoomRepository.findById(roomId)
//                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));
//
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Chat> chatMessagesPage = chatRepository.findByChatRoomOrderByCreatedAtDesc(room, pageable);
//
//        return chatMessagesPage.stream()
//                .map(ChatDto::new)
//                .collect(Collectors.toList());
//
//    }
}
