package com.example.capstone2.guesthouse.dto;

import lombok.Data;

@Data
public class BedRequest {
    private double xLocationRatio;
    private double yLocationRatio;
    private int floor;
}
