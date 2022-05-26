package com.example.capstone2.chat.service;

import com.example.capstone2.chat.dao.ChatMessageRepository;
import com.example.capstone2.chat.dao.ChatRoomRepository;
import com.example.capstone2.chat.dao.PublicChatRoomRepository;
import com.example.capstone2.chat.dao.ReservedChatRoomRepository;
import com.example.capstone2.chat.dto.ChatDto;
import com.example.capstone2.chat.entity.ChatMessage;
import com.example.capstone2.chat.entity.ChatRoom;
import com.example.capstone2.chat.entity.PublicChatRoom;
import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.guesthouse.service.RoomService;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final PublicChatRoomRepository publicChatRoomRepository;
    private final ReservedChatRoomRepository reservedChatRoomRepository;
    private final RoomRepository roomRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    @Transactional
    public ChatDto createChatMessage(Long chatRoomId, String email, String message, String dtype) throws IOException {
        User user = userService.findByEmail(email);
        Room room = roomRepository.getById(chatRoomId);
        ChatRoom chatRoom = chatRoomRepository.findByRoomAndDTYPE(room, dtype)
                .orElseThrow(() -> new IOException("채팅방을 찾을 수 없습니다."));

        ChatMessage chatMessage = ChatMessage.of(user, chatRoom, message);
        chatMessageRepository.save(chatMessage);

        return ChatDto.of(email, message);
    }

    @Transactional
    public ChatDto createReservedChatMessage(Long chatRoomId, String email, String message) {
//        User user = userService.findByEmail(email);
        return null;
    }
}
