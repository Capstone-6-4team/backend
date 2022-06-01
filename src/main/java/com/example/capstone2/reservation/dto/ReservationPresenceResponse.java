package com.example.capstone2.reservation.dto;

import lombok.Getter;

@Getter
public class ReservationPresenceResponse {
    private boolean reservationPresence;

    public ReservationPresenceResponse (boolean isTrue){
        this.reservationPresence=isTrue;
    }
}
