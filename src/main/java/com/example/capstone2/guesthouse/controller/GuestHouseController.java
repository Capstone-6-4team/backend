package com.example.capstone2.guesthouse.controller;

import com.example.capstone2.common.entity.HttpResponseDto;
import com.example.capstone2.guesthouse.dto.*;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.service.GuestHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_HOST')")
    public ResponseEntity<GuesthouseRegisterResponse> registerGuesthouse(Authentication authentication,
                                                                         @RequestParam("guestHouseName") String gName,
                                                                         @RequestParam("location") String location,
                                                                         @RequestParam("specificLocation") String specificLocation,
                                                                         @RequestPart("files") List<MultipartFile> files,
                                                                         @RequestPart("thumbnail") MultipartFile thumbnail){

        Long guestHouseId=null;
        GuesthouseRegisterResponse response=null;

        String email = authentication.getName();

        try{
            guestHouseId = guestHouseService.createGuestHouse(email, gName, location, specificLocation, files, thumbnail);
        }
        catch(Exception e){
            e.printStackTrace();
            if(e instanceof IllegalStateException){
                response = GuesthouseRegisterResponse.from(null, "Already registered house!");
                return ResponseEntity.badRequest().body(response);
            }

            else if(e instanceof IOException){
                response = GuesthouseRegisterResponse.from(null, "Image file save rejected!");
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.internalServerError().build();
        }

        response = GuesthouseRegisterResponse.from(String.valueOf(guestHouseId), "GuestHouse registration complete");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/guesthouse/room")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_HOST')")
    public ResponseEntity<String> registerRooms(Authentication authentication,
                                                @RequestParam("guestHouseId") String guestHouseId,
                                                @RequestParam("room") String rooms,
                                                @RequestParam("bed") String beds,
                                                @RequestPart("blueprint") List<MultipartFile> blueprints,
                                                @RequestPart("files") List<MultipartFile> multipartFiles){

        String email = authentication.getName();

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

    @GetMapping("/guesthouse/location")
    public ResponseEntity<List<GuestHouseDto>> findGuestHousesByCity(@RequestParam("city")String city){
        List<GuestHouseDto> result = guestHouseService.findRandomGuestHouseListByCity(city);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<GuestHouseListDto>> findAllByAddress(@RequestParam String city, @RequestParam String district) {
        return ResponseEntity.ok(guestHouseService.findAllByAddress(city, district));
    }
}
