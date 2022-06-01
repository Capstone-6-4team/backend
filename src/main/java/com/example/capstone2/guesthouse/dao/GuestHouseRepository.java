package com.example.capstone2.guesthouse.dao;

import com.example.capstone2.guesthouse.entity.GuestHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestHouseRepository extends JpaRepository<GuestHouse, Long> {
    List<GuestHouse> findByCity(String city);
}
