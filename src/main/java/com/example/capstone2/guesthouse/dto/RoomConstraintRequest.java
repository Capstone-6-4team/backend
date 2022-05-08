//package com.example.capstone2.guesthouse.vo;
//
//import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
//import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.Embeddable;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//
//@Getter @Setter
//@ToString
//@Embeddable
//public class RoomConstraintVO {
//
//    private boolean smoke;
//
//    private GenderConstraint genderConstraint;
//
//    public RoomConstraint toRoomConstraint(boolean smoke, GenderConstraint genderConstraint){
//        RoomConstraint build = RoomConstraint.builder()
//                .smoke(smoke)
//                .genderConstraint(genderConstraint)
//                .build();
//
//        return build;
//    }
//}
