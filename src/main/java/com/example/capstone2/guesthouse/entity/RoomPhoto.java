package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.common.entity.Photo;
import lombok.Builder;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roomphoto")
public class RoomPhoto extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    Photo photo;

    public static RoomPhoto of(Room room, String filePath, String fileName) {
        RoomPhoto roomPhoto = new RoomPhoto();
        roomPhoto.room = room;
        roomPhoto.photo = Photo.of(filePath, fileName);

        return roomPhoto;
    }

}
