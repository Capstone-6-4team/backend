package com.example.capstone2.guesthouse.dto;

import lombok.Data;

@Data
public class GuesthouseRegisterResponse {
    private String guestHouseId;
    private String message;

    public static GuesthouseRegisterResponse from(String id, String msg){
        GuesthouseRegisterResponse ghRegisterResponse = new GuesthouseRegisterResponse();
        ghRegisterResponse.guestHouseId=id;
        ghRegisterResponse.message=msg;

        return ghRegisterResponse;
    }
}
