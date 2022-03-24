package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Entity
public class Room extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guesthouse_id", nullable = false)
    private GuestHouse guestHouse;

    @NotNull
    @Positive
    private int capacity;

    @NotNull
    @Positive
    private int price;

    @Embedded
    private RoomConstraint roomConstraint;
}
