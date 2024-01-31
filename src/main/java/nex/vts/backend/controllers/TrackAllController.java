package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.TrackAllInfo;
import nex.vts.backend.repositories.DriverInfo_Repo;
import nex.vts.backend.repositories.OverSpeedRepo;
import nex.vts.backend.repositories.TrackAllRepo;
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

import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class TrackAllController {

    private final Logger logger = LoggerFactory.getLogger(TrackAllController.class);
    private final BaseResponse baseResponse = new BaseResponse();
    private final short API_VERSION = 1;


    @Autowired
    DriverInfo_Repo DriveRepo;

    @Autowired
    Environment environment;

    @Autowired
    TrackAllRepo trackAllRepo;

    @Autowired
    OverSpeedRepo overSpeedRepo;

    @Autowired
    ObjectMapper objectMapper;

    //v1/1/users/11118/4035/2/track_all
    @GetMapping(value = "v1/{deviceType}/users/{userId}/{p_userId}/{userType}/track_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> trackAll(@PathVariable("userId") Long userId,
                                           @PathVariable("p_userId") Long p_userId, @PathVariable("deviceType") int deviceType,
                                           @PathVariable("userType") int userType) throws JsonProcessingException {


        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        userId = deObfuscateId(userId);
        // p_userId = deObfuscateId(p_userId);


        Optional<ArrayList<TrackAllInfo>> trackAllInfos = trackAllRepo.getOverSpeedInfo(userType, userId, p_userId, deviceType, API_VERSION);

        if (trackAllInfos.isPresent()) {
            baseResponse.data = trackAllInfos;
            baseResponse.status = false;
            baseResponse.apiName = "trackAllInfos";
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.apiName = "trackAllInfos";
            baseResponse.data = trackAllInfos;
        }
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }
}
