package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.common.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="thumbnail")
public class Thumbnail extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guesthouse_id")
    private GuestHouse guestHouse;

    @Embedded
    Photo photo;

    public static Thumbnail of(GuestHouse guestHouse, String filePath, String fileName){
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.guestHouse = guestHouse;
        thumbnail.photo = Photo.of(filePath, fileName);

        return thumbnail;
    }
}
