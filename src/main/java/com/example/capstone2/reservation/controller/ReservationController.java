package com.example.capstone2.reservation.controller;

import com.example.capstone2.reservation.dto.ReservationCreateRequest;
import com.example.capstone2.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(value = "/create")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_GUEST')")
    public ResponseEntity<?> createReservation(Authentication authentication,
                                               @RequestBody ReservationCreateRequest request) {
        String email = authentication.getName();
        System.out.println("email = " + email);

        reservationService.create(email, request);

        return ResponseEntity.ok().build();
    }
}
