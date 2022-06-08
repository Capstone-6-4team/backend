package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User host;

    @Setter
    @OneToOne(mappedBy = "guestHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Thumbnail thumbnail;

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuestHousePhoto> guestHousePhotos=new ArrayList<>();

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

    private String city;
    private String district;
    private String roadName;
    private int buildingNum;
    private String specificLocation;

    public static GuestHouse of(User user, String guestHouseName, Double latitude, Double longitude,
                                String city, String district, String roadName, int buildingNum, String specificLocation){
        GuestHouse guestHouse = new GuestHouse();
        guestHouse.host=user;
        guestHouse.guestHouseName=guestHouseName;
        guestHouse.latitude=latitude;
        guestHouse.longitude=longitude;
        guestHouse.city=city;
        guestHouse.district=district;
        guestHouse.roadName=roadName;
        guestHouse.buildingNum=buildingNum;
        guestHouse.specificLocation=specificLocation;

        return guestHouse;
    }

    public void changeThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void addPhoto(GuestHousePhoto guestHousePhoto) {
        this.guestHousePhotos.add(guestHousePhoto);
    }
    public String getFullAddress() {
        List<String> address = new ArrayList<>();
        address.add(getCity());
        address.add(getDistrict());
        address.add(getRoadName());
        address.add(Integer.toString(getBuildingNum()));
        address.add(getSpecificLocation());
        return String.join(" ", address);
    }
}
