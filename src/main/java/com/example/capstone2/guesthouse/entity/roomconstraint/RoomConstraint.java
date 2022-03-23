package com.example.capstone2.guesthouse.entity.roomconstraint;

import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;

import javax.persistence.Embeddable;

@Embeddable
public class RoomConstraint {

    boolean smoke;
    GenderConstraint genderConstraint;
}
