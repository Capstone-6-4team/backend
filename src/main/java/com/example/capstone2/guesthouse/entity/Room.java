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

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bed> beds = new ArrayList<>();

    @Setter
    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Blueprint blueprint;

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

    public static Room of(List<RoomPhoto> roomPhotos, List<Bed> bedList, GuestHouse guestHouse, String roomName,
                          int capacity, int price, RoomConstraint roomConstraint, Blueprint blueprint){
        Room room = new Room();

        for(RoomPhoto roomPhoto : roomPhotos){
            roomPhoto.setRoom(room);
        }
        for(Bed bed : bedList){
            bed.setRoom(room);
        }
        blueprint.setRoom(room);

        room.guestHouse=guestHouse;
        room.roomName=roomName;
        room.capacity=capacity;
        room.price=price;
        room.roomConstraint=roomConstraint;
        room.blueprint=blueprint;
        room.roomPhotos=roomPhotos;
        room.beds=bedList;

        return room;
    }
}
