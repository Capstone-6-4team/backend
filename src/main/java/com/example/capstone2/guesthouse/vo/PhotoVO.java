package com.example.capstone2.guesthouse.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Getter @Setter
@ToString
@Embeddable
public class PhotoVO {

    private String fileName;
    private String filePath;
}
