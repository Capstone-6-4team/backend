package com.example.capstone2.chat.entity;

import com.example.capstone2.guesthouse.entity.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublicChatRoom extends ChatRoom {
    public static PublicChatRoom of(Room room) {
        PublicChatRoom publicChatRoom = new PublicChatRoom(room);
        return publicChatRoom;
    }

    protected PublicChatRoom(Room room) {
        super(room);
    }
}
