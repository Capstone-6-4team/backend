package com.example.capstone2.review.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class GuestHouseReview extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guesthouse_id")
    private GuestHouse guestHouse;

    @Range(min = 0, max = 5)
    @NotNull
    private int score;

    private String comment;
}
