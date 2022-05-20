package com.example.capstone2.guesthouse.controller;

import com.example.capstone2.common.entity.HttpResponseDto;
import com.example.capstone2.common.entity.StatusEnum;
import com.example.capstone2.guesthouse.dto.RoomDetailResponse;
import com.example.capstone2.guesthouse.dto.RoomResponse;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.guesthouse.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping(value = "/detail/{roomId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<RoomDetailResponse> getRoomDetail(@PathVariable Long roomId) {
        RoomDetailResponse roomDetail = roomService.getRoomDetail(roomId);
        return ResponseEntity.ok(roomDetail);
    }

    @GetMapping("/{guestHouseId}/roomList/{pageNum}/{pageSize}")
    public ResponseEntity<Page<RoomResponse>> getRooms(@PathVariable Long guestHouseId,
                                                       @PathVariable int pageNum, @PathVariable int pageSize){

        Page<RoomResponse> result = roomService.findRooms(guestHouseId, pageNum, pageSize);

        return ResponseEntity.ok(result);
    }
}
