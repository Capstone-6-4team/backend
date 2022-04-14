package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.common.entity.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class GuestHousePhoto extends BaseEntity {

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guesthouse_id")
    private GuestHouse guestHouse;

    @Embedded
    Photo photo;

    public static GuestHousePhoto of(String filePath, String fileName) {
        GuestHousePhoto guestHousePhoto = new GuestHousePhoto();
//        guestHousePhoto.guestHouse = guestHouse;
        guestHousePhoto.photo = Photo.of(filePath, fileName);

        return guestHousePhoto;
    }

}
