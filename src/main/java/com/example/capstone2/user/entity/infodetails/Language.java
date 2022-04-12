package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entity.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Language extends BaseEntity {

    @OneToMany(mappedBy = "language", cascade = CascadeType.REMOVE)
    private List<AvailableLanguage> availableLanguage;

    @Column(unique = true)
    String language;
}
