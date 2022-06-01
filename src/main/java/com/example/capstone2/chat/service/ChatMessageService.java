package com.example.capstone2.chat.service;

import com.example.capstone2.chat.dao.ChatMessageRepository;
import com.example.capstone2.chat.dao.ChatRoomRepository;
import com.example.capstone2.chat.dao.PublicChatRoomRepository;
import com.example.capstone2.chat.dao.ReservedChatRoomRepository;
import com.example.capstone2.chat.dto.ChatDto;
import com.example.capstone2.chat.dto.ChatRoomInfoDto;
import com.example.capstone2.chat.entity.ChatMessage;
import com.example.capstone2.chat.entity.ChatRoom;
import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.reservation.dao.ReservationRepository;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    @Transactional
    public ChatDto createChatMessage(Long roomId, String email, String message, String dtype) throws IOException {
        User user = userService.findByEmail(email);
        Room room = roomRepository.getById(roomId);
        ChatRoom chatRoom = chatRoomRepository.findByRoomAndDTYPE(room, dtype)
                .orElseThrow(() -> new IOException("채팅방을 찾을 수 없습니다."));

        ChatMessage chatMessage = ChatMessage.of(user, chatRoom, message);
        chatMessageRepository.save(chatMessage);

        return ChatDto.from(chatMessage);
    }

    @Transactional
    public ChatDto createReservedChatMessage(Long chatRoomId, String email, String message) {
//        User user = userService.findByEmail(email);
        return null;
    }

    @Transactional(readOnly = true)
    public List<ChatDto> getPrevChatList(Long chatRoomId) {
        return chatMessageRepository.findAllByChatRoomId(chatRoomId).stream()
                .map(ChatDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChatRoomInfoDto getChatRoomInfo(Long roomId, String dtype) {
        Room room = roomRepository.findById(roomId).get();
        ChatRoom chatRoom = chatRoomRepository.findByRoomAndDTYPE(room, dtype).get();
        return ChatRoomInfoDto.from(chatRoom);
    }

    public Boolean hasReservation(String email, Long roomId) {
        User user = userService.findByEmail(email);
        return reservationRepository.existsByUserAndRoomId(user, roomId);
    }
}
