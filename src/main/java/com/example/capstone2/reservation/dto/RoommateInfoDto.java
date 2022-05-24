package com.example.capstone2.reservation.dto;

import com.example.capstone2.reservation.entitiy.Reservation;
import com.example.capstone2.user.entity.Gender;
import com.example.capstone2.user.entity.infodetails.MBTI;
import com.example.capstone2.user.entity.infodetails.UserCharacteristic;
import lombok.Getter;

@Getter
public class RoommateInfoDto {
    private boolean smoke;
    private boolean drinking;
    private Gender gender;
    private Long bedId;
    private String nationality;
    private MBTI mbti;
    private String sleepPattern;

    public static RoommateInfoDto from(Reservation reservation) {
        UserCharacteristic characteristic = reservation.getUser().getCharacteristic();
        RoommateInfoDto dto = new RoommateInfoDto();
        dto.smoke = characteristic.isSmoke();
        dto.drinking = characteristic.isDrinking();
        dto.gender = characteristic.getGender();
        dto.bedId = reservation.getBed().getId();
        dto.nationality = characteristic.getNationality().getCode();
        dto.mbti = characteristic.getMbti();
        dto.sleepPattern = characteristic.getSleepPattern().getName();

        return dto;
    }
}
