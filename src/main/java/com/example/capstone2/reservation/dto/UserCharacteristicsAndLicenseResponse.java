package com.example.capstone2.reservation.dto;

import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.entity.infodetails.SleepPattern;
import lombok.Getter;

@Getter
public class UserCharacteristicsAndLicenseResponse {
    private boolean reservationPresence;
    private boolean smoke;
    private boolean drinking;
    private SleepPattern sleepPattern;

    public UserCharacteristicsAndLicenseResponse(boolean isTrue, User user){
        this.reservationPresence=isTrue;
        this.smoke=user.getCharacteristic().isSmoke();
        this.drinking=user.getCharacteristic().isDrinking();
        this.sleepPattern=user.getCharacteristic().getSleepPattern();
    }
}
