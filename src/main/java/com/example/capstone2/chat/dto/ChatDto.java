package com.example.capstone2.chat.dto;

import com.example.capstone2.chat.entity.ChatMessage;
import com.example.capstone2.user.entity.User;
import lombok.Getter;

@Getter
public class ChatDto {
    private Long userId;
    private String username;
    private String message;

    public static ChatDto from(ChatMessage chatMessage) {
        ChatDto chatDto = new ChatDto();
        User user = chatMessage.getUser();

        chatDto.userId = user.getId();
        chatDto.username = user.getName();
        chatDto.message = chatMessage.getMessage();

        return chatDto;
    }
}
