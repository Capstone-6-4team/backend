package com.example.capstone2.guesthouse.dto;

import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomName;
    private int capacity;
    private int price;
    private RoomConstraint roomConstraint;
    private byte[] data;
    private String contentType;
}
