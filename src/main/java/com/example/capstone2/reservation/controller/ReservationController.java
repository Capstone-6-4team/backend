package com.example.capstone2.reservation.controller;

import com.example.capstone2.reservation.dto.*;
import com.example.capstone2.reservation.service.ReservationService;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @PostMapping(value = "/create")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_GUEST')")
    public ResponseEntity<?> createReservation(Authentication authentication,
                                               @RequestBody ReservationCreateRequest request) {
        String email = authentication.getName();
        reservationService.create(email, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/roommate-info")
//    @PreAuthorize("isAuthenticated() and hasRole('ROLE_GUEST')")
    public ResponseEntity<List<RoommateInfoDto>> getRoommateInfo(@ModelAttribute RoommateInfoRequest request) {
        System.out.println("request.getRoomId() = " + request.getRoomId());
        System.out.println("request.getCheckInDate() = " + request.getCheckInDate());
        return ResponseEntity.ok(reservationService.getRoommateInfo(request));
    }

    @GetMapping(value = "/roommate-info-license")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserCharacteristicsAndLicenseResponse> hasBeenReserved(Authentication authentication,
                                                                                 @ModelAttribute ReservationPresenceRequest request){
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        boolean isTrue = reservationService.reservationPresence(email, request);

        return ResponseEntity.ok(new UserCharacteristicsAndLicenseResponse(isTrue, user));
    }
}
