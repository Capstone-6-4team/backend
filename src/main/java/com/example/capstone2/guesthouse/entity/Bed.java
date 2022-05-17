package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name="bed")
public class Bed extends BaseEntity {

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    private double xLocationRatio;

    @NotNull
    private double yLocationRatio;

    @NotNull
    private int floor;

    public static Bed of(double x, double y, int f){
        Bed bed = new Bed();
        bed.xLocationRatio=x;
        bed.yLocationRatio=y;
        bed.floor=f;

        return bed;
    }
}
