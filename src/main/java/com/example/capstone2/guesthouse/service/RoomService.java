package com.example.capstone2.guesthouse.service;

import com.example.capstone2.guesthouse.dao.GuestHouseRepository;
import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.dto.RoomDetailResponse;
import com.example.capstone2.guesthouse.dto.RoomResponse;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final GuestHouseRepository guestHouseRepository;

    @Transactional(readOnly = true)
    public RoomDetailResponse getRoomDetail(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("[" + roomId + "] 에 해당하는 방이 존재하지 않습니다."));
        return RoomDetailResponse.from(room);
    }

    @Transactional
    public Page<RoomResponse> findRooms(Long guestHouseId, int pageNum, int pageSize){
        GuestHouse guestHouse = guestHouseRepository.findById(guestHouseId).orElse(null);

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<Room> all = roomRepository.findByGuestHouse(guestHouse, pageable);
        Page<RoomResponse> pagedRoomResponse = all.map(room -> {
            FileInputStream fileInputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String contentType = null;

            String filePath = room.getRoomPhotos().get(0).getPhoto().getFilePath();
            String fileName = room.getRoomPhotos().get(0).getPhoto().getFileName();
            String fileDir = filePath + "\\" + fileName;
            Path path = Paths.get(fileDir);

            try {
                fileInputStream = new FileInputStream(fileDir);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int readCount = 0;
            byte[] buffer = new byte[1024];
            byte[] fileArray = null;

            try{
                while((readCount = fileInputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, readCount);
                }
                fileArray=byteArrayOutputStream.toByteArray();
                fileInputStream.close();
                byteArrayOutputStream.close();

                contentType = Files.probeContentType(path);
            }catch(IOException e){
                throw new RuntimeException("File Error");
            }

            return new RoomResponse(room.getId(), room.getRoomName(), room.getCapacity(), room.getPrice(),
                    room.getRoomConstraint(), fileArray, contentType);
            }
        );

        return pagedRoomResponse;
    }
}
