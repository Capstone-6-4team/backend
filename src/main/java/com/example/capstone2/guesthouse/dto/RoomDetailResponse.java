package com.example.capstone2.guesthouse.dto;

import com.example.capstone2.guesthouse.entity.Bed;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RoomDetailResponse {
    List<byte[]> photos;
    private String guestHouseName;
    private String roomName;
    RoomConstraint roomConstraint;
    private int price;
    String address;
    byte[] blueprint;
    private String contentType;
    List<BedResponse> beds;

    public static RoomDetailResponse from(Room room) {
        RoomDetailResponse response = new RoomDetailResponse();
        byte[] blueprintBytes = null;

        GuestHouse guestHouse = room.getGuestHouse();
        String location = guestHouse.getCity() + guestHouse.getDistrict() +
                guestHouse.getRoadName() + guestHouse.getBuildingNum();

//        List<BedResponse> bedResponseList = new ArrayList<>();

        response.guestHouseName = room.getGuestHouse().getGuestHouseName();
        response.roomName = room.getRoomName();
        response.price = room.getPrice();
        response.roomConstraint = room.getRoomConstraint();
        response.address = location;

        FileSystemResource blueprintResource = new FileSystemResource(room.getBlueprint().getPhoto().fullPath());
        try {
            blueprintBytes = blueprintResource.getInputStream().readAllBytes();
            response.blueprint=blueprintBytes;
            response.contentType= Files.probeContentType(Path.of(room.getBlueprint().getPhoto().fullPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        List<Bed> beds = room.getBeds();
//        for(Bed b : beds){
//            bedResponseList.add(BedResponse.from(b));
//        }
//        response.beds=bedResponseList;

        response.beds = room.getBeds().stream().map(bed -> BedResponse.from(bed)).collect(Collectors.toList());

        response.photos = room.getRoomPhotos().stream().map(roomPhoto -> {
            FileSystemResource resource = new FileSystemResource(roomPhoto.getPhoto().fullPath());
            try {
                return resource.getInputStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        return response;
    }
}
