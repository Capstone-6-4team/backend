package com.example.capstone2.review.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.reservation.entitiy.Reservation;
import com.example.capstone2.user.entity.User;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class UserReview extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @NotNull
    @Range(min = 0, max = 5)
    private int mannerScore;

    private String comment;
}
