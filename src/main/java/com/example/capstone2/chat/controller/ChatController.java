package com.example.capstone2.chat.controller;

import com.example.capstone2.chat.dto.ChatDto;
import com.example.capstone2.chat.dto.ChatRoomInfoDto;
import com.example.capstone2.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/public/{roomId}")
    @SendTo("/sub/public/{roomId}")
    public ChatDto publicBroadcastChat(Authentication authentication, @DestinationVariable Long roomId, String message) throws IOException {
        String username = authentication.getName();
        System.out.println("message = " + message);
        return chatMessageService.createChatMessage(roomId, username, message, "P");
    }

    @MessageMapping("/private/{roomId}")
    @SendTo("/sub/private/{roomId}")
    public ChatDto privateBroadcastChat(Authentication authentication, @DestinationVariable Long roomId, String message) throws IOException {
        String username = authentication.getName();
        return chatMessageService.createChatMessage(roomId, username, message, "R");
    }

    @GetMapping(value = "/prev-chat/{roomId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChatDto>> getPrevChatList(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatMessageService.getPrevChatList(roomId));
    }

    @GetMapping(value = "/public/room-info/{roomId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChatRoomInfoDto> getPublicChatRoomInfo(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatMessageService.getChatRoomInfo(roomId, "P"));
    }

    @GetMapping(value = "/private/room-info/{roomId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChatRoomInfoDto> getPrivateChatRoomInfo(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatMessageService.getChatRoomInfo(roomId, "R"));
    }

}
