package com.example.capstone2.reservation.service;

import com.example.capstone2.guesthouse.entity.Bed;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.guesthouse.service.GuestHouseService;
import com.example.capstone2.reservation.dao.ReservationRepository;
import com.example.capstone2.reservation.dto.ReservationCreateRequest;
import com.example.capstone2.reservation.dto.ReservationPresenceRequest;
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
        Bed bed = guestHouseService.findBedById(request.getBedId());
        Room room = bed.getRoom();

        Reservation reservation = Reservation.of(user, room, bed, request.getCheckInDate(), request.getCheckOutDate());
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

    @Transactional(readOnly = true)
    public boolean reservationPresence(String email, ReservationPresenceRequest request){
        User user = userService.findByEmail(email);
        if(user==null) throw new NullPointerException("유저가 null입니다.");
        if(request.getLocalDate()==null) throw new NullPointerException("date가 null입니다.");
        return reservationRepository.existsByUser(user, request.getLocalDate());
    }
}
