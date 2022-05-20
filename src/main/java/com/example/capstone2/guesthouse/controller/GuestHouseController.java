package com.example.capstone2.guesthouse.controller;

import com.example.capstone2.common.entity.HttpResponseDto;
import com.example.capstone2.guesthouse.dto.BedRequest;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.service.GuestHouseService;
import com.example.capstone2.guesthouse.dto.RoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping(value = "/api/house")
@RequiredArgsConstructor
public class GuestHouseController {
    private final GuestHouseService guestHouseService;

    @PostMapping("/register/guesthouse")
    public ResponseEntity<String> registerGuesthouse(@RequestParam("guestHouseName") String gName,
                                                     @RequestParam("location") String location,
                                                     @RequestPart("files") List<MultipartFile> files,
                                                    @RequestPart("thumbnail") MultipartFile thumbnail){
        GuestHouse guestHouse=null;
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Long guestHouseId=null;

        try{
            guestHouseId = guestHouseService.createGuestHouse(gName, location, files, thumbnail);
        }
        catch(Exception e){
            e.printStackTrace();
            if(e instanceof IllegalStateException)
                return ResponseEntity.badRequest().body("Already registered house!");
            else if(e instanceof IOException)
                return ResponseEntity.badRequest().body("Image file save rejected!");

            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(String.valueOf(guestHouseId));
    }

    @PostMapping("/register/guesthouse/room")
    public ResponseEntity<String> registerRooms(@RequestParam("guestHouseId") String guestHouseId,
                                                         @RequestParam("room") String rooms,
                                                         @RequestParam("bed") String beds,
                                                         @RequestPart("blueprint") List<MultipartFile> blueprints,
                                                         @RequestPart("files") List<MultipartFile> multipartFiles){
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<RoomRequest> roomRequests = guestHouseService.jsonToRoomRequestList(rooms);
        List<BedRequest> bedRequests = guestHouseService.jsonToBedRequestList(beds);

        try{
            guestHouseService.createGuestHouseRooms(roomRequests, bedRequests, guestHouseId, blueprints, multipartFiles);
        }catch(Exception e){
            e.printStackTrace();
            if (e instanceof IOException){
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.ok("Room list registration successfully completed!");
    }
}
