package com.example.capstone2.reservation.entitiy;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.Bed;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bed_id", nullable = false)
    private Bed bed;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public static Reservation of(User user, Room room, Bed bed, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation reservation = new Reservation();
        reservation.user = user;
        reservation.room = room;
        reservation.bed = bed;
        reservation.checkInDate = checkInDate;
        reservation.checkOutDate = checkOutDate;

        return reservation;
    }
}
