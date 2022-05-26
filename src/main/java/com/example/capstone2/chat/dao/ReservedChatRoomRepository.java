package com.example.capstone2.chat.dao;

import com.example.capstone2.chat.entity.ReservedChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedChatRoomRepository extends JpaRepository<ReservedChatRoom, Long> {
}
