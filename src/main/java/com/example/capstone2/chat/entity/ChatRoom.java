package com.example.capstone2.chat.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "dtype", insertable = false, updatable = false)
    private String DTYPE;

    protected ChatRoom(Room room) {
        this.room = room;
    }
}
