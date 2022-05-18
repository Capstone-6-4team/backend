package com.example.capstone2.guesthouse.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.common.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="blueprint")
public class Blueprint extends BaseEntity {

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    Photo photo;

    public static Blueprint of(String filePath, String fileName){
        Blueprint blueprint = new Blueprint();
        blueprint.photo = Photo.of(filePath, fileName);

        return blueprint;
    }
}
