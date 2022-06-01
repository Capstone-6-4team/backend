package com.example.capstone2.chat.service;

import com.example.capstone2.chat.dao.ChatRoomRepository;
import com.example.capstone2.chat.dao.PublicChatRoomRepository;
import com.example.capstone2.chat.dao.ReservedChatRoomRepository;
import com.example.capstone2.chat.entity.PublicChatRoom;
import com.example.capstone2.chat.entity.ReservedChatRoom;
import com.example.capstone2.guesthouse.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PublicChatRoomRepository publicChatRoomRepository;
    private final ReservedChatRoomRepository reservedChatRoomRepository;

    @Transactional
    public void createPublicChatRoom(Room room) {
        PublicChatRoom publicChatRoom = PublicChatRoom.of(room);
        publicChatRoomRepository.save(publicChatRoom);
    }

    @Transactional
    public void createdReservedChatRoom(Room room) {
        ReservedChatRoom reservedChatRoom = ReservedChatRoom.of(room);
        reservedChatRoomRepository.save(reservedChatRoom);
    }
}
