package com.example.capstone2.guesthouse.entity.roomconstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor
@Getter
public class RoomConstraint {

//    @NotNull
    boolean smoke;

    @Enumerated(value = EnumType.ORDINAL)
    GenderConstraint genderConstraint;

    public static RoomConstraint of(boolean smoke, GenderConstraint genderConstraint) {
        RoomConstraint roomConstraint = new RoomConstraint();
        roomConstraint.smoke=smoke;
        roomConstraint.genderConstraint=genderConstraint;

        return roomConstraint;
    }
}
