package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.SpeedDataModel;
import nex.vts.backend.models.responses.TravelDistanceDataModel;
import nex.vts.backend.repositories.TravelDistanceDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlTravelDistanceData {

    private TravelDistanceDataModel reqBody = null;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TravelDistanceDataRepo travelDistanceDataRepo;

    private final Logger logger = LoggerFactory.getLogger(CtrlTravelDistanceData.class);


    @GetMapping(value = "/v1/{deviceType}/users/{userId}/getTravelDistanceData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTravelDistanceData(@RequestParam Map<String, String> requestBody, @PathVariable("userId") Integer userId) throws JsonProcessingException {
        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }

           reqBody = objectMapper.readValue(requestBody.get("data"), TravelDistanceDataModel.class);
           travelDistanceDataRepo.getTravelDistanceData(reqBody);


        return null;
    }

    }
