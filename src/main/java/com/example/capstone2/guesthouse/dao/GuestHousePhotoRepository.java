package com.example.capstone2.guesthouse.dao;

import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.entity.GuestHousePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestHousePhotoRepository extends JpaRepository<GuestHousePhoto, Long> {
//    List<GuestHousePhoto> findAllByGuesthouseId();

    List<GuestHousePhoto> findAllByGuestHouse(GuestHouse guestHouse);
}
