package com.example.capstone2.guesthouse.controller;

import com.example.capstone2.guesthouse.dto.RoomDetailResponse;
import com.example.capstone2.guesthouse.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
