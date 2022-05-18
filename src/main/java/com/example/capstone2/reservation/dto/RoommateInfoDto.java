package com.example.capstone2.reservation.dto;

import com.example.capstone2.reservation.entitiy.Reservation;
import com.example.capstone2.user.entity.Gender;
import com.example.capstone2.user.entity.infodetails.UserCharacteristic;
import lombok.Getter;

@Getter
public class RoommateInfoDto {
    private boolean smoke;
    private boolean drinking;
    private Gender gender;
    private int bedNumber;

    public static RoommateInfoDto from(Reservation reservation) {
        UserCharacteristic characteristic = reservation.getUser().getCharacteristic();
        RoommateInfoDto dto = new RoommateInfoDto();
        dto.smoke = characteristic.isSmoke();
        dto.drinking = characteristic.isDrinking();
        dto.gender = characteristic.getGender();
        dto.bedNumber = 0;

        return dto;
    }
}
