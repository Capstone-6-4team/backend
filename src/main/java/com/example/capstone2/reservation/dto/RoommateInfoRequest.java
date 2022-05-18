package com.example.capstone2.reservation.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RoommateInfoRequest {
    private Long roomId;
    private LocalDate checkInDate;

    public RoommateInfoRequest(Long roomId, String checkInDate) {
        this.roomId = roomId;
        this.checkInDate = LocalDate.parse(checkInDate);
    }
}
