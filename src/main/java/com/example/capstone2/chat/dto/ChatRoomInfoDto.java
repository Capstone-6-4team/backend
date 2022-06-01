package com.example.capstone2.chat.dto;

import com.example.capstone2.chat.entity.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomInfoDto {
    private Long chatRoomId;

    public static ChatRoomInfoDto from(ChatRoom chatRoom) {
        ChatRoomInfoDto dto = new ChatRoomInfoDto();
        dto.chatRoomId = chatRoom.getId();
        return dto;
    }
}
