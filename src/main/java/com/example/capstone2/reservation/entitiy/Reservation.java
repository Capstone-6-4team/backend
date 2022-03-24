package com.example.capstone2.reservation.entitiy;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.user.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    LocalDate checkInDate;
    LocalDate checkOutDate;
}
