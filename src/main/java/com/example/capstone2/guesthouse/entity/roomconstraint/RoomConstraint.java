package com.example.capstone2.guesthouse.entity.roomconstraint;

import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Embeddable
public class RoomConstraint {

    @NotNull
    boolean smoke;

    @Enumerated(value = EnumType.ORDINAL)
    GenderConstraint genderConstraint;
}
