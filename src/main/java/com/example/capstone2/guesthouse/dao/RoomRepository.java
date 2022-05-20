package com.example.capstone2.guesthouse.dao;

import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findByGuestHouse(GuestHouse guestHouse, Pageable pageable);
}
