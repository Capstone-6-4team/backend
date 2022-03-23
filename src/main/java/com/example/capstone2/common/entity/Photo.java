package com.example.capstone2.common.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class Photo extends BaseEntity{

    String filePath;
    String fileName;
}
