package com.example.capstone2.chat.dto;

import com.example.capstone2.chat.entity.ChatMessage;
import lombok.Getter;

@Getter
public class ChatDto {
    private String username;
    private String message;

    public static ChatDto of(String username, String message) {
        ChatDto chatDto = new ChatDto();
        chatDto.username = username;
        chatDto.message = message;
        return chatDto;
    }
}
