package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.SpeedDataRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;
import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlSpeedData {
    private final short API_VERSION = 1;
    private final Logger logger = LoggerFactory.getLogger(CtrlSpeedData.class);

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Environment environment;
    @Autowired
    SpeedDataRepo speedDataRepo;

    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/speedData/{fromDate}/{timeSlot}/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSpeedData(@PathVariable("deviceType") Integer deviceType,
                                               @PathVariable("vehicleId") Integer vehicleId,
                                               @PathVariable("timeSlot") Integer timeslot,
                                               @PathVariable("fromDate") String fromDate,
                                               @PathVariable(value = "userId") Long userId) throws JsonProcessingException {
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
        userId = (long) Math.toIntExact(deObfuscateId(userId));
        vehicleId = Math.toIntExact(deObfuscateId(Long.valueOf(vehicleId)));

        /* Input Validation */
        SpeedDataModel reqBody = new SpeedDataModel(fromDate, timeslot, vehicleId);
        Optional<ArrayList<SpeedDataResponse>> speedDataResponses;
        if (reqBody.getTimeSlot() == 24) {
            String fromTime, toTime;
            fromTime = reqBody.getFromDate().substring(0, 8); /*2022/03/14  10:42:40*/
            Integer changeToTime = Integer.valueOf(fromTime) + 1;
            String finalFromTime = fromTime.concat("020000"), finalToTime = String.valueOf(changeToTime).concat("015959");
            speedDataResponses = speedDataRepo.getSpeedDataForgr(finalToTime, finalFromTime, reqBody.getVehicleId(), deviceType);
        } else {
            String fromTime, toTime, finalFromTime;
            fromTime = reqBody.getFromDate().substring(0, 8);
            Integer timeSlot = reqBody.getTimeSlot() + 2;
            if (Integer.toString(timeSlot).length() == 1) {
                finalFromTime = fromTime.concat("0" + timeSlot + "0000");
                toTime = fromTime.concat("0" + timeSlot + "5959");
            } else {
                finalFromTime = fromTime.concat(timeSlot + "0000");
                toTime = fromTime.concat(timeSlot + "5959");
            }
            speedDataResponses = speedDataRepo.getSpeedDataForgr(toTime, finalFromTime, reqBody.getVehicleId(), deviceType);
            System.out.println();
        }
        BaseResponse baseResponse = new BaseResponse();
        if (speedDataResponses.isEmpty()) {
            speedDataResponsesObj speedDataResponsesObj = new speedDataResponsesObj();
            baseResponse.status = false;
            speedDataResponsesObj.setSpeedDataResponses(Optional.of(new ArrayList<SpeedDataResponse>()));
            baseResponse.data = speedDataResponsesObj;
            baseResponse.errorMsg = "Data  not found within this time limit";
            baseResponse.errorCode = 4041;
            baseResponse.apiName = "getSpeedData";
        } else {
            speedDataResponsesObj speedDataResponsesObj = new speedDataResponsesObj();
            baseResponse.status = true;
            speedDataResponsesObj.setSpeedDataResponses(speedDataResponses);
            baseResponse.data = speedDataResponsesObj;
            baseResponse.apiName = "getSpeedData";
        }
        System.out.println(ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse)));
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
        //return ResponseEntity.status(httpStatus).headers(httpHeaders).body(clientResponse);
        //return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse),API_VERSION));

    }

}
