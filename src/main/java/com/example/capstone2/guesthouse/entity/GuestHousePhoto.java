package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.Photo;

import javax.persistence.*;

@Entity
@DiscriminatorValue("G")
public class GuestHousePhoto extends Photo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guesthouse_id", nullable = false)
    private GuestHouse guestHouse;
}
