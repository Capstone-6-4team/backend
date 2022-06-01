package com.example.capstone2.reservation.dao;

import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.reservation.entitiy.Reservation;
import com.example.capstone2.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {
    List<Reservation> findAllRelated(Room room, LocalDate checkInDate);
    boolean existsByUser(User usr, LocalDate date);
}
