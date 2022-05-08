package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Room extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guesthouse_id")
    private GuestHouse guestHouse;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomPhoto> roomPhotos = new ArrayList<>();

    @NotNull
    private String roomName;

    @NotNull
    @Positive
    private int capacity;

    @NotNull
    @Positive
    private int price;

    @Embedded
    private RoomConstraint roomConstraint;

    public static Room of(GuestHouse guestHouse, String roomName, int capacity, int price,
                          RoomConstraint roomConstraint){
        Room room = new Room();

        room.guestHouse=guestHouse;
        room.roomName=roomName;
        room.capacity=capacity;
        room.price=price;
        room.roomConstraint=roomConstraint;

        return room;
    }
}
