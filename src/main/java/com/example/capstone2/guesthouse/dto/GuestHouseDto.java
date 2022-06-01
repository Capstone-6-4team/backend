package com.example.capstone2.guesthouse.dto;

import com.example.capstone2.guesthouse.entity.GuestHouse;
import lombok.Data;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class GuestHouseDto {
    private Long id;
    private String guestHouseName;
    private String city;
    private byte[] data;
    private String contentType;

    public static GuestHouseDto from(GuestHouse guestHouse) {
        GuestHouseDto guestHouseDto = new GuestHouseDto();
        FileInputStream fileInputStream=null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        guestHouseDto.id= guestHouse.getId();
        guestHouseDto.guestHouseName=guestHouse.getGuestHouseName();
        guestHouseDto.city=guestHouse.getCity();

        String filePath = guestHouse.getThumbnail().getPhoto().getFilePath();
        String fileName = guestHouse.getThumbnail().getPhoto().getFileName();
        String fileDir = filePath + "\\" + fileName;

        Path path = Paths.get(fileDir);

        try {
            fileInputStream = new FileInputStream(fileDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try {
            while((readCount = fileInputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, readCount);
            }
            fileArray=byteArrayOutputStream.toByteArray();

            guestHouseDto.data=fileArray;

            guestHouseDto.contentType = Files.probeContentType(path);
            fileInputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return guestHouseDto;
    }
}
