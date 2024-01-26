package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehDriverInfo;
import nex.vts.backend.repositories.DriverInfo_Repo;
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

import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class VehDetailsController {

    private final Logger logger = LoggerFactory.getLogger(VehDetailsController.class);
    private final BaseResponse baseResponse = new BaseResponse();
    private final short API_VERSION = 1;


    @Autowired
    DriverInfo_Repo DriveRepo;
    @Autowired
    Environment environment;

    @Autowired
    ObjectMapper objectMapper;


    //http://localhost:8009/api/private/v1/1/users/1/1/vehicle/details/13293

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/vehicle/details/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "/api/v1/content/live-tv/playing-data", produces = "application/json; charset=UTF-8;", method = RequestMethod.POST)
    public ResponseEntity<String> Get_VehicleDetails(@PathVariable("vehicleId") Long vehicleId,
                                             @PathVariable("userId") Long userId,
                                             @PathVariable("deviceType") int deviceType,
                                             @PathVariable("userType") int userType) throws JsonProcessingException {
        //
        // 
        String _API_NAME = "GetVehicleInfo";
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        userId = deObfuscateId(userId);
        vehicleId = deObfuscateId(vehicleId);
        //
        //

        //-----------FIELD-VALIDATION---------------->>>
        Optional<ArrayList<VehDriverInfo>> Get_DriverInfo = DriveRepo.get_DriverInfo(
                String.valueOf(userId), Math.toIntExact(vehicleId), deviceType, userType
        );
        //-----------FIELD-VALIDATION----------------<<<


        // SET:: RESPONSE DATA --->> /start/
        if (Get_DriverInfo.isEmpty()) {
            baseResponse.data = new ArrayList<>();
            baseResponse.status = false;
            baseResponse.apiName = _API_NAME;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.apiName = _API_NAME;
            baseResponse.data = Get_DriverInfo;
        }
        // SET:: RESPONSE DATA ---<< /end/
        //
        //
        //:: RESPONSE-PRINT ---
        try {
            return new ResponseEntity<>(objectMapper.writeValueAsString(baseResponse), HttpStatus.OK);
        } catch (JsonProcessingException ex) {
            logger.error("Preparing response error: " + ex.getMessage());
        }
        throw new AppCommonException(4001 + "##Invalid response##" + deviceType + "##" + API_VERSION);
        //throw new CustomErrorException("ERR-002.011", "Invalid response!");


    }



}

