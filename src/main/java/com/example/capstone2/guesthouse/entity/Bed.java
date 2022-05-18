package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.dto.BedRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name="bed")
public class Bed extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    private double xLocationRatio;

    @NotNull
    private double yLocationRatio;

    @NotNull
    private int floor;

    public static Bed of(Room room, BedRequest bedRequest) {
        Bed bed = new Bed();
        bed.room = room;
        bed.xLocationRatio = bedRequest.getXLocationRatio();
        bed.yLocationRatio = bedRequest.getYLocationRatio();
        bed.floor = bedRequest.getFloor();

        return bed;
    }
}
