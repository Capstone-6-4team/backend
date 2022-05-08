package com.example.capstone2.guesthouse.dto;

import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
import lombok.Data;

@Data
public class RoomRequest {
    private String roomName;
    private int capacity;
    private int price;
    private int numOfPhoto;
    private boolean smoke;
    private GenderConstraint genderConstraint;
}
