package com.example.capstone2.chat.dao;

import com.example.capstone2.chat.entity.PublicChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicChatRoomRepository extends JpaRepository<PublicChatRoom, Long> {
}
