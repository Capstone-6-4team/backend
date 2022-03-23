package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class GuestHouse extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User host;

    @OneToOne
    @Column(name = "thumbnail_photo_id")
    private GuestHousePhoto thumbnail;
    // 위치 정보 추가해야함!
}
