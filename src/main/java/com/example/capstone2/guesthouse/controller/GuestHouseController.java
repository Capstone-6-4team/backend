package com.example.capstone2.guesthouse.controller;

import com.example.capstone2.common.entity.HttpResponseDto;
import com.example.capstone2.common.entity.StatusEnum;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.service.GuestHouseService;
//import com.example.capstone2.guesthouse.vo.RoomConstraintVO;
import com.example.capstone2.guesthouse.dto.RoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        try{
            guestHouseService.createGuestHouse(gName, location, files, thumbnail);
        }
        catch(Exception e){
            e.printStackTrace();
            if(e instanceof IllegalStateException)
                return ResponseEntity.badRequest().body("Already registered house!");
            else if(e instanceof IOException)
                return ResponseEntity.badRequest().body("Image file save rejected!");

            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("House registration successfully completed!");
    }

    @PostMapping("/register/guesthouse/room")
    public ResponseEntity<String> registerRooms(@RequestParam("guestHouseId") String guestHouseId,
                                                         @RequestParam("room") String roomInfoJson,
                                                         @RequestPart("files") List<MultipartFile> multipartFile){
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try{
            guestHouseService.createGuestHouseRooms(roomInfoJson, guestHouseId, multipartFile);
        }catch(Exception e){
            e.printStackTrace();
            if (e instanceof IOException){
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.ok("Room list registration successfully completed!");
    }

//    @GetMapping("/guesthouse_list")
//    public ResponseEntity<HttpResponseDto> getEveryGuestHouse(){
//
//        HttpResponseDto body = new HttpResponseDto();
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//
//    }
}
