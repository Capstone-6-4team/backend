package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Room extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "guesthouse_id", nullable = false)
    private GuestHouse guestHouse;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int price;

    @Embedded
    private RoomConstraint roomConstraint;
}
