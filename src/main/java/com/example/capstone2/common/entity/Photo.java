package com.example.capstone2.common.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

@Embeddable
@Getter
public class Photo{

    @NotNull
    String filePath;
    @NotNull
    String fileName;

    public static Photo of(String filePath, String fileName) {
        Photo photo = new Photo();
        photo.filePath = filePath;
        photo.fileName = fileName;

        return photo;
    }

    public String fullPath() {
        return Path.of(filePath, fileName).toString();
    }
}
