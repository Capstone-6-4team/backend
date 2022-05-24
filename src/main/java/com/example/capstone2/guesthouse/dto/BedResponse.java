package com.example.capstone2.guesthouse.dto;

import com.example.capstone2.guesthouse.entity.Bed;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BedResponse {
    private Long bedId;
    private double xLocationRatio;
    private double yLocationRatio;
    private int floor;

    public static BedResponse from(Bed bed){
        BedResponse bedResponse=new BedResponse();
        bedResponse.bedId=bed.getId();
        bedResponse.xLocationRatio=bed.getXLocationRatio();
        bedResponse.yLocationRatio=bed.getYLocationRatio();
        bedResponse.floor=bed.getFloor();

        return bedResponse;
    }
}
