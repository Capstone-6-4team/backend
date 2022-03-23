package com.example.capstone2.reservation.entitiy;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.user.entity.User;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Reservation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Room room;

    LocalDate checkInDate;
    LocalDate checkOutDate;
}
