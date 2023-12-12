package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.ChatRoomDto;
import com.jjans.BB.Entity.ChatRoom;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.ChatRoomRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.ChatRoomService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UsersRepository userRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, UsersRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ChatRoomDto> getAllChatRoomsByUser() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        List<ChatRoom> userChatRooms = chatRoomRepository.findByParticipantsContaining(user);

        return userChatRooms.stream()
                .map(ChatRoomDto::new)
                .collect(Collectors.toList());    }

    @Override
    public ChatRoomDto createChatRoom(String nickname) {

        String userEmail = SecurityUtil.getCurrentUserEmail();
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Users receiver = userRepository.findByNickName(nickname).
                orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.addParticipant(user);
        chatRoom.addParticipant(receiver);

        chatRoomRepository.save(chatRoom);

        return new ChatRoomDto(chatRoom);
    }

    @Override
    public boolean existsRoom(Long roomId) {
        return chatRoomRepository.findById(roomId).isPresent();
    }
}
