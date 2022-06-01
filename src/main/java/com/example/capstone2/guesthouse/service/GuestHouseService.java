package com.example.capstone2.guesthouse.service;

import com.example.capstone2.guesthouse.dao.BedRepository;
import com.example.capstone2.guesthouse.dao.GuestHouseRepository;
import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.entity.*;
import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import com.example.capstone2.guesthouse.dto.*;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@RequiredArgsConstructor
@Service
public class GuestHouseService {
    private final GuestHouseRepository guestHouseRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Value("${kakao.api-key}")
    private String GEOCODE_USER_INFO;
    private static String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
    private static final String UPLOAD_PATH = Path.of(System.getProperty("user.dir"),
            "directory", "photos").toString();

    @Getter
    @AllArgsConstructor
    private enum Category {
        ROOM("rooms"),
        GUESTHOUSE("guesthouse"),
        THUMBNAIL("thumbnail");

        private String name;
    }


    @Transactional
    public Long createGuestHouse(String email, String ghName,
                                       String location, String specificLocation, List<MultipartFile> multipartFiles,
                                       MultipartFile multipartFile) throws IOException {

        User user = userService.findByEmail(email);

        HashMap<String, String> locationInfo = convertAddressToLatitudeLongitude(location);

        Double latitude = Double.valueOf(locationInfo.get("latitude"));
        Double longitude = Double.valueOf(locationInfo.get("longitude"));
        String regionName1 = locationInfo.get("city");
        String regionName2 = locationInfo.get("district");
        String roadName = locationInfo.get("roadName");
        int buildingNum = Integer.valueOf(locationInfo.get("buildingNum"));

        GuestHouse guestHouse = GuestHouse.of(user, ghName, latitude, longitude, regionName1, regionName2, roadName, buildingNum, specificLocation);
        // 디렉토리 경로 중 tempUser 부분은 유저 구현이 끝나면 실제 유저 이름으로 대체
        String path = Path.of(UPLOAD_PATH, Category.GUESTHOUSE.getName(), ghName).toString();

        List<MultipartFile> files=multipartFiles;
        for (MultipartFile file : files){
            String fileId = saveEachPhoto(path, file);
            GuestHousePhoto guestHousePhoto = GuestHousePhoto.of(guestHouse, path, fileId);
            guestHouse.addPhoto(guestHousePhoto);
        }

        path = Path.of(UPLOAD_PATH, Category.THUMBNAIL.getName(), ghName).toString();
        String thumbnailId = saveEachPhoto(path, multipartFile);
        Thumbnail thumbnail = Thumbnail.of(guestHouse, path, thumbnailId);
        guestHouse.changeThumbnail(thumbnail);

        guestHouseRepository.save(guestHouse);

        return guestHouse.getId();
    }

    @Transactional
    public void createGuestHouseRooms(List<RoomRequest> roomRequests, List<BedRequest> beds, String ghId,
                                      List<MultipartFile> blueprints, List<MultipartFile> files) throws IOException {

        Long id = Long.valueOf(ghId);
        GuestHouse guestHouse = guestHouseRepository.getById(id);

        List<BedRequest> bRequests = new ArrayList<>(beds); // asList로 생성된 ArrayList는 remove 함수를 지원하지 않기 때문

        for(RoomRequest roomRequest : roomRequests){
            boolean smoke = roomRequest.isSmoke();
            GenderConstraint gConstraint = roomRequest.getGenderConstraint();
            RoomConstraint rConstraint = RoomConstraint.of(smoke, gConstraint);

            int numOfPhoto = roomRequest.getNumOfPhoto();
            int numOfBed = roomRequest.getNumOfBed();

            String blueprintPath = Path.of(UPLOAD_PATH, "blueprint", guestHouse.getGuestHouseName(),
                    roomRequest.getRoomName()).toString();

            MultipartFile blueprintFile = blueprints.get(0);
            String blueprintId = saveEachPhoto(blueprintPath, blueprintFile);
            Blueprint blueprint = Blueprint.of(blueprintPath, blueprintId);
            blueprints.remove(0);

            Room room = Room.of(guestHouse, roomRequest.getRoomName(),
                    roomRequest.getCapacity(), roomRequest.getPrice(), rConstraint, blueprint);

            String roomPhotosPath = Path.of(UPLOAD_PATH, "roomPhotos", guestHouse.getGuestHouseName(),
                    roomRequest.getRoomName()).toString();
            // 각 방의 사진을 표현하는 방식을 바꾸는 것이 좋을 듯
            for(int i = 0; i < numOfPhoto; i++) {
                MultipartFile file = files.get(0);
                String fileId = saveEachPhoto(roomPhotosPath, file);
                RoomPhoto roomPhoto = RoomPhoto.of(room, roomPhotosPath, fileId);
                room.addPhoto(roomPhoto);
                files.remove(0);
            }

            for(int i = 0; i < numOfBed; i++){
                BedRequest bedRequest = bRequests.get(0);
                Bed bed = Bed.of(room, bedRequest);
                room.addBed(bed);
                bRequests.remove(0);
            }

            roomRepository.save(room);
        }
    }

    private String saveEachPhoto(String path, MultipartFile file) throws IOException {
        String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
        String originName = file.getOriginalFilename(); // ex) 파일.jpg
        String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
        String fileName = fileId + "." + fileExtension;
//        originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
        long fileSize = file.getSize(); // 파일 사이즈
        File fileSave = new File(path, fileName); // ex) fileId.jpg
        if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
            fileSave.mkdirs();
        }

        file.transferTo(fileSave); // fileSave의 형태로 파일 저장


        return fileName;
    }

    @Transactional(readOnly = true)
    public Room findRoomById(Long roomId) {
        return roomRepository.getById(roomId);
    }

    @Transactional(readOnly = true)
    public Bed findBedById(Long bedId) {
        return bedRepository.getById(bedId);
    }

    @Transactional(readOnly = true)
    public List<GuestHouseDto> findRandomGuestHouseListByCity(String city){
        List<GuestHouse> guestHouseListByCity = guestHouseRepository.findByCity(city);
        List<GuestHouseDto> dtoList = guestHouseListByCity.stream().map(GuestHouseDto::from).collect(Collectors.toList());

        Collections.shuffle(dtoList); // randomly shuffle elements

        return dtoList.stream().limit(10).collect(Collectors.toList());
    }

    private HashMap<String, String> convertAddressToLatitudeLongitude(String addr) throws IOException {
        URL obj;
        HashMap<String, String> locationInfo=new HashMap<>();

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

            System.out.println("documents = " + documents);

            for(int i=0;i<documents.length();i++){
                JSONObject object = documents.getJSONObject(i);
                JSONObject address1 = object.getJSONObject("road_address");

                String latitude = address1.getString("x");
                String longitude = address1.getString("y");
                String regionName1 = address1.getString("region_1depth_name");
                String regionName2 = address1.getString("region_2depth_name");
                String roadName = address1.getString("road_name");
                String buildingNum = address1.getString("main_building_no");

                locationInfo.put("latitude", latitude);
                locationInfo.put("longitude", longitude);
                locationInfo.put("city", regionName1);
                locationInfo.put("district", regionName2);
                locationInfo.put("roadName", roadName);
                locationInfo.put("buildingNum", buildingNum);
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
