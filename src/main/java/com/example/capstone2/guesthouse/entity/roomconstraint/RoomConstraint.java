package com.example.capstone2.guesthouse.entity.roomconstraint;

import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
import com.example.capstone2.user.entity.Gender;
import lombok.Builder;
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

    @Builder
    public RoomConstraint(boolean smoke, GenderConstraint genderConstraint){
        this.smoke=smoke;
        this.genderConstraint=genderConstraint;
    }
}
