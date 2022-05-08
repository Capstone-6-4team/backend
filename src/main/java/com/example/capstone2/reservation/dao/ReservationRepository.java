package com.example.capstone2.reservation.dao;

import com.example.capstone2.reservation.entitiy.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
}
