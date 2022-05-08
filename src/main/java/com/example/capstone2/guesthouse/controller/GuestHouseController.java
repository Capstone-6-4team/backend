package com.example.capstone2.guesthouse.controller;

import com.example.capstone2.common.entity.HttpResponseDto;
import com.example.capstone2.common.entity.StatusEnum;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/house")
@RequiredArgsConstructor
public class GuestHouseController {
    private final GuestHouseService guestHouseService;

    @PostMapping("/register/guesthouse")
    public ResponseEntity<HttpResponseDto> registerGuesthouse(@RequestParam("guestHouseName") String gName,
                                                     @RequestParam("location") String location,
                                                     @RequestPart("files") List<MultipartFile> files,
                                                    @RequestPart("thumbnail") MultipartFile thumbnail){
        GuestHouse guestHouse=null;
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try{
            guestHouse = guestHouseService.createGuestHouse(gName, location, files, thumbnail);
        }
        catch(Exception e){
            e.printStackTrace();
            if(e instanceof IllegalStateException){
                body.setData(null);
                body.setMessage("Already registered house!");
                body.setHttpStatus(StatusEnum.BAD_REQUEST);
            }
            else if(e instanceof IOException){
                body.setData(null);
                body.setMessage("Image file save rejected!");
                body.setHttpStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            }
        }

        body.setData(guestHouse.getId());
        body.setMessage("House registration successfully completed!");
        body.setHttpStatus(StatusEnum.OK);

        return new ResponseEntity<>(body, headers, HttpStatus.CREATED);
    }

    @PostMapping("/register/guesthouse/room")
    public ResponseEntity<HttpResponseDto> registerRooms(@RequestParam("guestHouseId") String guestHouseId,
                                                         @RequestParam("room") List<String> rooms,
                                                         @RequestPart("files") List<MultipartFile> multipartFile){
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<RoomRequest> roomRequests = guestHouseService.convertStringToListRoomRequest(rooms);

        try{
            guestHouseService.createGuestHouseRooms(roomRequests, guestHouseId, multipartFile);
        }catch(Exception e){
            e.printStackTrace();
            if (e instanceof IOException){
                body.setMessage("Image file save rejected!");
                body.setHttpStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            }
        }

        body.setMessage("Room list registration successfully completed!");
        body.setHttpStatus(StatusEnum.OK);

        return new ResponseEntity<>(body, headers, HttpStatus.CREATED);
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
