package com.example.capstone2.guesthouse.service;

import com.example.capstone2.guesthouse.dao.GuestHouseRepository;
import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.entity.*;
import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import com.example.capstone2.guesthouse.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @Value("${kakao.api-key}")
    private String GEOCODE_USER_INFO;
    private static String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";

    public GuestHouse createGuestHouse(String ghName,
                                       String location, List<MultipartFile> multipartFiles,
                                       MultipartFile multipartFile) throws IOException {

        HashMap<String, Double> locationInfo = convertAddressToLatitudeLongitude(location);
        Double latitude = locationInfo.get("latitude");
        Double longitude = locationInfo.get("longitude");

        // 디렉토리 경로 중 tempUser 부분은 유저 구현이 끝나면 실제 유저 이름으로 대체
        String UPLOAD_PATH = System.getProperty("user.dir") + "\\directory\\pictures\\tempUser\\" + ghName;

        List<GuestHousePhoto> ghPhotos = new ArrayList<>();

        List<MultipartFile> files=multipartFiles;
        for (MultipartFile file : files){
            String fileId = saveEachPhoto(UPLOAD_PATH, file, "photos");
            GuestHousePhoto ghP = GuestHousePhoto.of(UPLOAD_PATH, fileId);
            ghPhotos.add(ghP);
        }

        String thumbnailId = saveEachPhoto(UPLOAD_PATH, multipartFile, "thumbnail");
        Thumbnail thumbnail = Thumbnail.of(UPLOAD_PATH, thumbnailId);

        GuestHouse gh = GuestHouse.of(ghPhotos, thumbnail, ghName, latitude, longitude, location);
        GuestHouse save = guestHouseRepository.save(gh);

        return save;
    }

    public void createGuestHouseRooms(List<RoomRequest> rooms, List<BedRequest> beds, String ghId,
                                      List<MultipartFile> blueprints, List<MultipartFile> files) throws IOException {
        Long id = Long.valueOf(ghId);
        String UPLOAD_PATH = System.getProperty("user.dir") + "\\directory\\pictures\\tempUser\\" + findGuestHouseNameById(id);

        List<BedRequest> bRequests = new ArrayList<>(beds); // asList로 생성된 ArrayList는 remove 함수를 지원하지 않기 때문

        for(RoomRequest room : rooms){
            boolean smoke = room.isSmoke();
            GenderConstraint gConstraint = room.getGenderConstraint();
            RoomConstraint rConstraint = RoomConstraint.of(smoke, gConstraint);

            int numOfPhoto = room.getNumOfPhoto();
            int numOfBed = room.getNumOfBed();

            GuestHouse guestHouse = guestHouseRepository.getById(id);

            MultipartFile blueprint = blueprints.get(0);
            String blueprintId = saveEachPhoto(UPLOAD_PATH, blueprint, "blueprint\\" + room.getRoomName());
            Blueprint of = Blueprint.of(UPLOAD_PATH, blueprintId);
            blueprints.remove(0);

            List<RoomPhoto> rPhotos = new ArrayList<>();

            for(int i=0;i<numOfPhoto;i++){
                MultipartFile file = files.get(0);
                String fileId = saveEachPhoto(UPLOAD_PATH, file, "rooms\\" + room.getRoomName());
                RoomPhoto rP = RoomPhoto.of(UPLOAD_PATH, fileId);
                rPhotos.add(rP);

                files.remove(0);
            }

            List<Bed> bedList = new ArrayList<>();

            for(int i=0;i<numOfBed;i++){
                BedRequest bedRequest = bRequests.get(0);
                Bed bed = Bed.of(bedRequest.getXLocationRatio(), bedRequest.getYLocationRatio(), bedRequest.getFloor());
                bedList.add(bed);
                bRequests.remove(0);
            }

            Room r = Room.of(rPhotos, bedList, guestHouse, room.getRoomName(),
                    room.getCapacity(), room.getPrice(), rConstraint, of);

            roomRepository.save(r);
        }
    }

    private String saveEachPhoto(String UPLOAD_PATH, MultipartFile file, String category) throws IOException {
        String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
        String originName = file.getOriginalFilename(); // ex) 파일.jpg
        String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg

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

    private HashMap<String, Double> convertAddressToLatitudeLongitude(String addr) throws IOException {
        URL obj;
        HashMap<String, Double> locationInfo=new HashMap<>();

        try{
            // 주소 표기 방식 : "대구광역시 중구 동성로2가 동성로2길 81"
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

                locationInfo.put("latitude", Double.valueOf(latitude));
                locationInfo.put("longitude", Double.valueOf(longitude));
            }

            return locationInfo;
        }catch(Exception e){
            e.printStackTrace();
        }
        return locationInfo;
    }

    public List<RoomRequest> jsonToRoomRequestList(String rooms) {
        
        List<RoomRequest> result = null;
        try {
            if(rooms.startsWith("[")) {
                result = Arrays.asList(objectMapper.readValue(rooms, RoomRequest[].class));
            } else {
                result = new ArrayList<>();
                result.add(objectMapper.readValue(rooms, RoomRequest.class));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<BedRequest> jsonToBedRequestList(String beds) {
        List<BedRequest> result=new ArrayList<>();
        try {
            if(beds.startsWith("[")) {
                result = Arrays.asList(objectMapper.readValue(beds, BedRequest[].class));
            } else {
                result = new ArrayList<>();
                result.add(objectMapper.readValue(beds, BedRequest.class));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
