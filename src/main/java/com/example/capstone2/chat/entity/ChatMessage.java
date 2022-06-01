package com.example.capstone2.chat.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String message;

    public static ChatMessage of(User user, ChatRoom chatRoom, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.user = user;
        chatMessage.chatRoom = chatRoom;
        chatMessage.message = message;

        return chatMessage;
    }
}
