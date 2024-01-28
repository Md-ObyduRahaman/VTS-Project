package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.OverSpeedData;
import nex.vts.backend.repositories.DriverInfo_Repo;
import nex.vts.backend.repositories.OverSpeedRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class OverSpeed_Controller {

    private final Logger logger = LoggerFactory.getLogger(OverSpeed_Controller.class);
    private final BaseResponse baseResponse = new BaseResponse();
    private final short API_VERSION = 1;


    @Autowired
    DriverInfo_Repo DriveRepo;

    @Autowired
    Environment environment;

    @Autowired
    OverSpeedRepo overSpeedRepo;

    @Autowired
    ObjectMapper objectMapper;

    ///v1/1/users/14461/1/overSpeed/0/20240125000000
    ///v1/1/users/38356386/0/1/overSpeed/0/20240125000000
    ///v1/{deviceType}/users/{userId}/{p_userId}/{userType}/overSpeed/details/{vehicleId}/{fromDate}/{toDate}
    ///v1/1/users/7215/7215/1/overSpeed/details/34108/20240125000000/20240125235959
//v1/1/user/19139994/19139994/1/overSpeed/details/0/20240115000000/20240128000000
    @GetMapping(value = "v1/{deviceType}/users/{userId}/{p_userId}/{userType}/overSpeed/details/{vehicleId}/{fromDate}/{toDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> overSpeed(@PathVariable("vehicleId") Long vehicleId, @PathVariable("userId") Long userId,
                                            @PathVariable("p_userId") Long p_userId, @PathVariable("deviceType") int deviceType,
                                            @PathVariable("userType") int userType, @PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate) throws JsonProcessingException {


        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        userId = deObfuscateId(userId);
        p_userId = deObfuscateId(p_userId);


        OverSpeedData overSpeedData = overSpeedRepo.getOverSpeedInfo("SPEED", "D", userType, userId, p_userId, 1, vehicleId, fromDate, toDate, deviceType);


        if (overSpeedData.getSpeedReportDetails().isEmpty()) {
            baseResponse.data = overSpeedData;
            baseResponse.status = false;
            baseResponse.apiName = "Over Speeding";
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.apiName = "Over Speeding";
            baseResponse.data = overSpeedData;
        }
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }
}

