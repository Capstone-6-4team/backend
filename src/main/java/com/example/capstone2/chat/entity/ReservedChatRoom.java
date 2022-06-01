package com.example.capstone2.chat.entity;

import com.example.capstone2.guesthouse.entity.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservedChatRoom extends ChatRoom {

    public static ReservedChatRoom of(Room room) {
        ReservedChatRoom reservedChatRoom = new ReservedChatRoom(room);
        return reservedChatRoom;
    }

    protected ReservedChatRoom(Room room) {
        super(room);
    }
}
