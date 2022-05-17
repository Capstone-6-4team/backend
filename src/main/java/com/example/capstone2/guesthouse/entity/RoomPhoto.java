package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.common.entity.Photo;
import lombok.Builder;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roomphoto")
public class RoomPhoto extends BaseEntity {

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    Photo photo;

    public static RoomPhoto of(String filePath, String fileName) {
        RoomPhoto roomPhoto = new RoomPhoto();
        roomPhoto.photo = Photo.of(filePath, fileName);

        return roomPhoto;
    }

}
