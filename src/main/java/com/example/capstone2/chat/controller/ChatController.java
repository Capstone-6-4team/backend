package com.example.capstone2.chat.controller;

import com.example.capstone2.chat.dto.ChatDto;
import com.example.capstone2.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/public/{roomId}")
    @SendTo("/sub/public/{roomId}")
    public ChatDto publicBroadcastChat(Authentication authentication, @DestinationVariable Long roomId, String message) throws IOException {
        String username = authentication.getName();
        return chatMessageService.createChatMessage(roomId, username, message, "P");
    }

    @MessageMapping("/reserved/{roomId}")
    @SendTo("/sub/public/{roomId}")
    public ChatDto privateBroadcastChat(Authentication authentication, @DestinationVariable Long roomId, String message) throws IOException {
        String username = authentication.getName();
        return chatMessageService.createChatMessage(roomId, username, message, "R");
    }

}
