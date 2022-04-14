package com.example.capstone2.guesthouse.service;

import com.example.capstone2.guesthouse.dao.GuestHouseRepository;
import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.entity.GuestHouse;
import com.example.capstone2.guesthouse.entity.GuestHousePhoto;
import com.example.capstone2.guesthouse.entity.Room;
import com.example.capstone2.guesthouse.entity.RoomPhoto;
import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import com.example.capstone2.guesthouse.vo.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class GuestHouseService {
    private final GuestHouseRepository guestHouseRepository;
    private final RoomRepository roomRepository;

    @Value("${kakao.api-key}")
    private String GEOCODE_USER_INFO;
    private static String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";

    public GuestHouse createGuestHouse(String ghName, String latitudeStr, String longitudeStr, String location,
                                       List<MultipartFile> multipartFiles, MultipartFile multipartFile) throws IOException {

        double latitude = Double.parseDouble(latitudeStr);
        double longitude = Double.parseDouble(longitudeStr);

        // 디렉토리 경로 중 tempUser 부분은 유저 구현이 끝나면 실제 유저 이름으로 대체
        String UPLOAD_PATH = System.getProperty("user.dir") + "\\directory\\pictures\\tempUser\\" + ghName;

        boolean empty = multipartFiles.isEmpty();
        System.out.println("empty = " + empty);

        GuestHouse gh=GuestHouse.builder()
                .guestHouseName(ghName)
                .latitude(latitude)
                .longitude(longitude)
                .location(location)
//                .thumbnail(thumbnail)
                .build();
        List<GuestHousePhoto> ghPhotos = gh.getGuestHousePhotos();

        List<MultipartFile> files=multipartFiles;
        for (MultipartFile file : files){
            String fileId = saveEachPhoto(UPLOAD_PATH, file, "photos");
            GuestHousePhoto ghP = GuestHousePhoto.of(UPLOAD_PATH, fileId);
            ghP.setGuestHouse(gh);
            ghPhotos.add(ghP);
        }

        GuestHouse save = guestHouseRepository.save(gh);

        return save;
    }

    public void createGuestHouseRooms(List<RoomVO> rooms, String ghId, List<MultipartFile> files) throws IOException {
        Long id = Long.valueOf(ghId);
//        String UPLOAD_PATH="C:\\pictures\\tempUser\\" + findGuestHouseNameById(id);
        String UPLOAD_PATH = System.getProperty("user.dir") + "\\directory\\pictures\\tempUser\\" + findGuestHouseNameById(id);

        for(RoomVO room : rooms){
            boolean smoke = room.getRoomConstraint().isSmoke();
            GenderConstraint gConstraint = room.getRoomConstraint().getGenderConstraint();
            RoomConstraint rConstraint = room.getRoomConstraint().toRoomConstraint(smoke, gConstraint);
            int numOfPhoto = room.getNumOfPhoto();

            GuestHouse guestHouse = guestHouseRepository.getById(id);
            Room r = Room.builder()
                    .roomName(room.getRoomName())
                    .capacity(room.getCapacity())
                    .price(room.getPrice())
                    .roomConstraint(rConstraint)
                    .guestHouse(guestHouse)
                    .build();

            List<RoomPhoto> rPhotos = r.getRoomPhotos();

            for(int i=0;i<numOfPhoto;i++){
                MultipartFile file = files.get(0);
                String fileId = saveEachPhoto(UPLOAD_PATH, file, "rooms\\" + room.getRoomName());
                RoomPhoto rP = RoomPhoto.of(UPLOAD_PATH, fileId);
                rP.setRoom(r);
                rPhotos.add(rP);

                files.remove(0);
            }

            roomRepository.save(r);
        }
    }

    private String saveEachPhoto(String UPLOAD_PATH, MultipartFile file, String category) throws IOException {
        String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
        String originName = file.getOriginalFilename(); // ex) 파일.jpg
        String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
        originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
        long fileSize = file.getSize(); // 파일 사이즈

        File fileSave = new File(UPLOAD_PATH + "\\" + category, fileId + "." + fileExtension); // ex) fileId.jpg
        if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
            fileSave.mkdirs();
        }

        file.transferTo(fileSave); // fileSave의 형태로 파일 저장


        return fileId;
    }

    private String findGuestHouseNameById(Long id) {
        GuestHouse result = guestHouseRepository.findById(id).orElse(null);
        return result.getGuestHouseName();
    }

    public String convertAddressToLatLong(String addr) throws IOException {
        URL obj;

        try{
            // 주소 표기 방식 : "대구광역시 중구 동성로2가 동성로2길 81"
            // 인코딩한 String을 넘겨야 원하는 데이터를 받을 수 있음
            String address = URLEncoder.encode(addr, "UTF-8");

            obj = new URL(GEOCODE_URL+address);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization","KakaoAK " + GEOCODE_USER_INFO);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // JSON에서 위도, 경도만 추출
            JSONObject jObject = new JSONObject(response.toString());
            JSONArray documents = jObject.getJSONArray("documents");

            for(int i=0;i<documents.length();i++){
                JSONObject object = documents.getJSONObject(i);
                JSONObject address1 = object.getJSONObject("address");

                String latitude = address1.getString("x");
                String longitude = address1.getString("y");

                System.out.println("latitude = " + latitude);
                System.out.println("longitude = " + longitude);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
