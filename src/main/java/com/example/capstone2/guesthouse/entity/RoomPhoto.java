package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.Photo;

import javax.persistence.*;

@Entity
@DiscriminatorValue("R")
public class RoomPhoto extends Photo {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Room room;
}
