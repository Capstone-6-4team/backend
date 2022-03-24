package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.Photo;

import javax.persistence.*;

@Entity
@DiscriminatorValue("R")
public class RoomPhoto extends Photo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
