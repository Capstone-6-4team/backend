package com.example.capstone2.guesthouse.service;

import com.example.capstone2.guesthouse.dao.GuestHouseRepository;
import com.example.capstone2.guesthouse.dao.RoomRepository;
import com.example.capstone2.guesthouse.entity.*;
import com.example.capstone2.guesthouse.entity.roomconstraint.GenderConstraint;
import com.example.capstone2.guesthouse.entity.roomconstraint.RoomConstraint;
import com.example.capstone2.guesthouse.dto.*;
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

    public GuestHouse createGuestHouse(String ghName,
                                       String location, List<MultipartFile> multipartFiles,
                                       MultipartFile multipartFile) throws IOException {

        HashMap<String, Double> locationInfo = convertAddressToLatitudeLongitude(location);
        Double latitude = locationInfo.get("latitude");
        System.out.println("latitude = " + latitude);
        Double longitude = locationInfo.get("longitude");
        System.out.println("longitude = " + longitude);

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

    public void createGuestHouseRooms(List<RoomRequest> rooms, String ghId, List<MultipartFile> files) throws IOException {
        Long id = Long.valueOf(ghId);
        String UPLOAD_PATH = System.getProperty("user.dir") + "\\directory\\pictures\\tempUser\\" + findGuestHouseNameById(id);

        for(RoomRequest room : rooms){
            boolean smoke = room.isSmoke();
            GenderConstraint gConstraint = room.getGenderConstraint();
            RoomConstraint rConstraint = RoomConstraint.of(smoke, gConstraint);

            int numOfPhoto = room.getNumOfPhoto();

            GuestHouse guestHouse = guestHouseRepository.getById(id);

            Room r = Room.of(guestHouse, room.getRoomName(),
                    room.getCapacity(), room.getPrice(), rConstraint);

            List<RoomPhoto> rPhotos = r.getRoomPhotos();

            for(int i=0;i<numOfPhoto;i++){
                MultipartFile file = files.get(0);
                String fileId = saveEachPhoto(UPLOAD_PATH, file, "rooms\\" + room.getRoomName());
                RoomPhoto rP = RoomPhoto.of(r, UPLOAD_PATH, fileId);
//                rP.setRoom(r);
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
//        originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
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

    private HashMap<String, Double> convertAddressToLatitudeLongitude(String addr) throws IOException {
        URL obj;
        HashMap<String, Double> locationInfo=new HashMap<>();

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

//                System.out.println("latitude = " + latitude);
//                System.out.println("longitude = " + longitude);

                locationInfo.put("latitude", Double.valueOf(latitude));
                locationInfo.put("longitude", Double.valueOf(longitude));
            }

            return locationInfo;
        }catch(Exception e){
            e.printStackTrace();
        }
        return locationInfo;
    }

    public List<RoomRequest> convertStringToListRoomRequest(List<String> rooms) {
        List<RoomRequest> result=new ArrayList<>();

        for(String room : rooms){
            RoomRequest roomRequest=new RoomRequest();

//            System.out.println("room = " + room);
            room=room.replace("{", "");
            room=room.replace("}", "");
            String[] stringNameValuePairs = room.split(",");

            for(String nameValuePair : stringNameValuePairs){
                String[] nameValue = nameValuePair.split(":");
                if(nameValue[0].equals("\"roomName\"")){
//                    System.out.println("roomName");
                    roomRequest.setRoomName(nameValue[1].replace("\"", ""));
                }
                else if(nameValue[0].equals("\"capacity\"")){
//                    System.out.println("capacity");
                    roomRequest.setCapacity(Integer.valueOf(nameValue[1]));
                }
                else if(nameValue[0].equals("\"price\"")){
//                    System.out.println("price");
                    roomRequest.setPrice(Integer.valueOf(nameValue[1]));
                }
                else if(nameValue[0].equals("\"numOfPhoto\"")){
//                    System.out.println("numOfPhoto");
                    roomRequest.setNumOfPhoto(Integer.valueOf(nameValue[1]));
                }
                else if(nameValue[0].equals("\"smoke\"")){
//                    System.out.println("smoke");
                    roomRequest.setSmoke(Boolean.valueOf(nameValue[1]));
                }
                else if(nameValue[0].equals("\"genderConstraint\"")){
//                    System.out.println("genderConstraint");
                    if(Integer.valueOf(nameValue[1])==0){
                        roomRequest.setGenderConstraint(GenderConstraint.MALE_ONLY);
                    }
                    else if(Integer.valueOf(nameValue[1])==1){
                        roomRequest.setGenderConstraint(GenderConstraint.FEMALE_ONLY);
                    }
                    else{
                        roomRequest.setGenderConstraint(GenderConstraint.MIXED);
                    }

                }
            }
//            System.out.println("roomRequest = " + roomRequest);
            result.add(roomRequest);
        }
        return result;
    }
}
