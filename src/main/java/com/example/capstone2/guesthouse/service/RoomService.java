package com.example.capstone2.guesthouse.service;

import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.dto.RoomDetailResponse;
import com.example.capstone2.guesthouse.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public RoomDetailResponse getRoomDetail(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("[" + roomId + "] 에 해당하는 방이 존재하지 않습니다."));
        return RoomDetailResponse.from(room);
    }
}
