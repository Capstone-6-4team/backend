package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Setter
    @OneToOne(mappedBy = "guestHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Thumbnail thumbnail;

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuestHousePhoto> guestHousePhotos=new ArrayList<>();

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private String location;

    public static GuestHouse of(String guestHouseName, Double latitude, Double longitude, String location){
        GuestHouse guestHouse = new GuestHouse();
        guestHouse.guestHouseName=guestHouseName;
        guestHouse.latitude=latitude;
        guestHouse.longitude=longitude;
        guestHouse.location=location;

        return guestHouse;
    }

    public void changeThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void addPhoto(GuestHousePhoto guestHousePhoto) {
        this.guestHousePhotos.add(guestHousePhoto);
    }
}
