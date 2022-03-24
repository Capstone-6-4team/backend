package com.example.capstone2.common.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class Photo extends BaseEntity{

    @NotNull
    String filePath;
    @NotNull
    String fileName;
}
