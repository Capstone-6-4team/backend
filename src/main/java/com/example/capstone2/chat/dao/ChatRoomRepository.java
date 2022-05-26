package com.example.capstone2.chat.dao;

import com.example.capstone2.chat.entity.ChatRoom;
import com.example.capstone2.guesthouse.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomAndDTYPE(Room room, String dtype);
}
