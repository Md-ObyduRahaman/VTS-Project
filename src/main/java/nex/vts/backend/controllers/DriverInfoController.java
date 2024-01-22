package nex.vts.backend.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.DriverInfoRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class DriverInfoController {

//    @Autowired
//    DriverInfoRepo DriveRepo;
//    @Autowired
//    Environment environment;
//    private final short API_VERSION = 1;
//    @Autowired
//    ObjectMapper objectMapper;
//    //http://localhost:8009/api/private/v1/1/users/1/1/DriverInfo/215
//    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/DriverInfo/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> DriverList(@PathVariable("vehicleId") Long vehicleId,
//                                             @PathVariable("userId") Long userId,
//                                             @PathVariable("deviceType") int deviceType,
//                                             @PathVariable("userType") int userType) throws JsonProcessingException {
//
//        String activeProfile = environment.getProperty("spring.profiles.active");
//        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
//
//        userId = deObfuscateId(userId);
//        vehicleId = deObfuscateId(vehicleId);
//
//        System.out.println("111");
//        System.exit(0);
//
//        Optional<ArrayList<VehDriverInfo>> Get_DriverInfo = DriveRepo.get_DriverInfo(
//                String.valueOf(userId),  Math.toIntExact(vehicleId), deviceType, userType
//        );
////        GetDriverInfo.get().setVehProfileChangePermision(DriveRepo.findVehProfileChangePermision(Math.toIntExact(vehicleId),userType).get());
//
//
//        //OUTPUT::
//        BaseResponse baseResponse = new BaseResponse();
//
//        if (Get_DriverInfo.isEmpty()) {
//            baseResponse.data=new ArrayList<>();
//            baseResponse.status = false;
//            baseResponse.apiName= "Get Driver Info";
//            baseResponse.errorMsg = "Data  not found";
//            baseResponse.errorCode = 4041;
//        } else {
//            baseResponse.status = true;
//            baseResponse.apiName= "Get Driver Info";
//            baseResponse.data =  Get_DriverInfo;
//        }
//
//
//        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
//       // return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse),API_VERSION));
//
//    }

}