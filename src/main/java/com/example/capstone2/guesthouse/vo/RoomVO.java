package com.example.capstone2.guesthouse.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;

@Getter @Setter
@ToString
@Data
public class RoomVO {
    private String roomName;
    private int capacity;
    private int price;
    private int numOfPhoto;

    @Embedded
    private RoomConstraintVO roomConstraint;
}
