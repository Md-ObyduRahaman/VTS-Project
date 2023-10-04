package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleOthersInfoModel;
import nex.vts.backend.repositories.VehicleOthersInfoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class CtrlGetVehicleSetting {

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    VehicleOthersInfoRepo vehicleOthersInfoRepo;

    private final Logger logger = LoggerFactory.getLogger(CtrlGetVehicleSetting.class);


    @GetMapping(value = "/v1/{deviceType}/users/{userId}/getVehicleSetting/{rowID}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getVehicleOthersInfo(@PathVariable("deviceType") Integer deviceType,
                                                     @PathVariable("userId") Integer userId,
                                                        @PathVariable("rowID") Integer rowID) throws JsonProcessingException {

        Optional<VehicleOthersInfoModel> vehicleOthersInfo= vehicleOthersInfoRepo.getVehicleOthersInfo(rowID);
        BaseResponse baseResponse = new BaseResponse();


        if (vehicleOthersInfo.isEmpty()) {
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.data = vehicleOthersInfo;
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }
}
