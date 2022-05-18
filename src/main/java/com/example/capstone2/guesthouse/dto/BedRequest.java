package com.example.capstone2.guesthouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BedRequest {
    @JsonProperty
    private double xLocationRatio;
    @JsonProperty
    private double yLocationRatio;
    private int floor;
}
