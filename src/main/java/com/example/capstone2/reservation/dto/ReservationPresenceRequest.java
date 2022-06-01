package com.example.capstone2.reservation.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationPresenceRequest {
    private LocalDate localDate;

    public ReservationPresenceRequest (String localDate){
        this.localDate=LocalDate.parse(localDate);
    }
}
