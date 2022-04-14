package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "guesthouse")
public class GuestHouse extends BaseEntity {

    private String guestHouseName;

//    user 구현 완료된 후 주석 해제
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User host;

//    @OneToOne
//    @JoinColumn(name = "thumbnail_photo_id")
//    private GuestHousePhoto thumbnail;

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuestHousePhoto> guestHousePhotos=new ArrayList<>();

    // 위치 정보 추가해야함!
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private String location;

    @Builder
    public GuestHouse(String guestHouseName, Double latitude, Double longitude, String location){
        this.guestHouseName=guestHouseName;
        this.latitude=latitude;
        this.longitude=longitude;
        this.location=location;
//        this.thumbnail=thumbnail;
    }

}
