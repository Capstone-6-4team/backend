package com.example.capstone2.reservation.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class ReservationCreateRequest {
    Long roomId;
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    LocalDate checkInDate;
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    LocalDate checkOutDate;
}
