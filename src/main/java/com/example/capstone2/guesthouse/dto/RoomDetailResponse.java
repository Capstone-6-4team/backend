package com.example.capstone2.guesthouse.dto;

import com.example.capstone2.guesthouse.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RoomDetailResponse {
    List<byte[]> photos;
    private String guestHouseName;
    private String roomName;
    private int price;

    public static RoomDetailResponse from(Room room) {
        RoomDetailResponse response = new RoomDetailResponse();
        response.guestHouseName = room.getGuestHouse().getGuestHouseName();
        response.roomName = room.getRoomName();
        response.price = room.getPrice();

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
