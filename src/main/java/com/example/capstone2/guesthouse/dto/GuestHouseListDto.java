package com.example.capstone2.guesthouse.dto;

import com.example.capstone2.guesthouse.entity.GuestHouse;
import lombok.Getter;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class GuestHouseListDto {
    private String name;
    private Long id;
    private byte[] thumbnail;
    private String contentType;
    private String address;

    public static GuestHouseListDto from(GuestHouse guestHouse) {
        GuestHouseListDto dto = new GuestHouseListDto();
        dto.name = guestHouse.getGuestHouseName();
        dto.id = guestHouse.getId();
        dto.address = guestHouse.getFullAddress();

        String path = guestHouse.getThumbnail().getPhoto().fullPath();
        FileSystemResource resource = new FileSystemResource(path);
        try {
            dto.thumbnail = resource.getInputStream().readAllBytes();
            dto.contentType = Files.probeContentType(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }
}
