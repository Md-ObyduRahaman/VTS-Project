package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.OverSpeedData;
import nex.vts.backend.models.responses.VehDriverInfo;
import nex.vts.backend.repositories.DriverInfoRepo;
import nex.vts.backend.repositories.OverSpeedRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
    DriverInfoRepo DriveRepo;
    @Autowired
    Environment environment;

    @Autowired
    OverSpeedRepo overSpeedRepo;

    @Autowired
    ObjectMapper objectMapper;

    ///v1/1/users/14461/1/overSpeed/0/20240125000000
    ///v1/1/users/38356386/0/1/overSpeed/0/20240125000000

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{p_userId}/{userType}/overSpeed/{vehicleId}/{dateTime}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> overSpeed(@PathVariable("vehicleId") Long vehicleId, @PathVariable("userId") Long userId,
                                            @PathVariable("p_userId") Long p_userId, @PathVariable("deviceType") int deviceType,
                                            @PathVariable("userType") int userType, @PathVariable("dateTime") String dateTime) throws JsonProcessingException {


        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        userId = deObfuscateId(userId);
        // p_userId = deObfuscateId(p_userId);
       // vehicleId = deObfuscateId(vehicleId);

        String fromDate = null,toDate = null;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

            // Parse the given date and time
            Date date = inputFormat.parse(dateTime);

            // Format the date to the desired format
            fromDate = outputFormat.format(date) + "000000";
             toDate = outputFormat.format(date) + "235959";

            System.out.println("From Date: " + fromDate);
            System.out.println("To Date: " + toDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<ArrayList<OverSpeedData>> overSpeedData = overSpeedRepo.getOverSpeedInfo("SPEED", "D", userType, userId, p_userId, 1, vehicleId, fromDate, toDate, deviceType);


        if (overSpeedData.isEmpty()) {
            baseResponse.data = new ArrayList<>();
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

