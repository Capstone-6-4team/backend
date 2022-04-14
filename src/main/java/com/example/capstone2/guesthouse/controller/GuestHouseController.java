package com.example.capstone2.guesthouse.controller;

import com.example.capstone2.common.entity.HttpResponseDto;
import com.example.capstone2.common.entity.StatusEnum;
import com.example.capstone2.guesthouse.service.GuestHouseService;
//import com.example.capstone2.guesthouse.vo.RoomConstraintVO;
import com.example.capstone2.guesthouse.vo.RoomVO;
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
@RequiredArgsConstructor
public class GuestHouseController {
    private final GuestHouseService guestHouseService;

    @PostMapping("/register/guesthouse")
    public ResponseEntity<HttpResponseDto> registerGuesthouse(@RequestParam("guestHouseName") String gName,
                                                     @RequestParam("latitude") String latStr,
                                                     @RequestParam("longitude") String longStr,
                                                     @RequestParam("location") String location,
                                                     @RequestPart("files") List<MultipartFile> files,
                                                    @RequestPart("thumbnail") MultipartFile thumbnail){
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try{
            guestHouseService.createGuestHouse(gName, latStr, longStr, location, files, thumbnail);
        }
        catch(Exception e){
            e.printStackTrace();
            if(e instanceof IllegalStateException){
                body.setMessage("Already registered house!");
                body.setHttpStatus(StatusEnum.BAD_REQUEST);
            }
            else if(e instanceof IOException){
                body.setMessage("Image file save rejected!");
                body.setHttpStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            }
        }
        body.setMessage("House registration successfully completed!");
        body.setHttpStatus(StatusEnum.OK);

        return new ResponseEntity<>(body, headers, HttpStatus.CREATED);
    }

    @PostMapping("/register/guesthouse/room")
    public ResponseEntity<HttpResponseDto> registerRooms(@RequestParam("guestHouseId") String guestHouseId,
                                                         @RequestPart("room") List<RoomVO> rooms,
                                                         @RequestPart("files") List<MultipartFile> multipartFile){
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try{
            guestHouseService.createGuestHouseRooms(rooms, guestHouseId, multipartFile);
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

    @GetMapping("/kakao/map/test")
    public ResponseEntity<HttpResponseDto> addressToLatitudeLongitude(@RequestParam("address") String address){
        HttpResponseDto body = new HttpResponseDto();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String result = null;

        try{
            result = guestHouseService.convertAddressToLatLong(address);
        }catch(Exception e){
            e.printStackTrace();
            body.setMessage("Informal address!");
            body.setHttpStatus(StatusEnum.INTERNAL_SERVER_ERROR);
        }

        body.setData(result);
        body.setMessage("Convert (Latitude, Longitude) to address successfully!");
        body.setHttpStatus(StatusEnum.OK);

        return new ResponseEntity<>(body, headers, HttpStatus.CREATED);
    }
}
