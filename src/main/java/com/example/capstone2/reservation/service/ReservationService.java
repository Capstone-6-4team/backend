package com.example.capstone2.reservation.service;

import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.guesthouse.service.GuestHouseService;
import com.example.capstone2.reservation.dao.ReservationRepository;
import com.example.capstone2.reservation.dto.ReservationCreateRequest;
import com.example.capstone2.reservation.dto.RoommateInfoDto;
import com.example.capstone2.reservation.dto.RoommateInfoRequest;
import com.example.capstone2.reservation.entitiy.Reservation;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final GuestHouseService guestHouseService;

    @Transactional
    public void create(String email, ReservationCreateRequest request) {
        User user = userService.findByEmail(email);
        Room room = guestHouseService.findRoomById(request.getRoomId());

        Reservation reservation = Reservation.of(user, room, request.getCheckInDate(), request.getCheckOutDate());
        reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<RoommateInfoDto> getRoommateInfo(RoommateInfoRequest request) {
        Room room = guestHouseService.findRoomById(request.getRoomId());
        List<Reservation> related = reservationRepository.findAllRelated(room, request.getCheckInDate());
        List<RoommateInfoDto> roommateInfoDtos = related.stream()
                .map(RoommateInfoDto::from)
                .collect(Collectors.toList());

        return roommateInfoDtos;
    }
}
