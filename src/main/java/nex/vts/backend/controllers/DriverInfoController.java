package nex.vts.backend.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.DriverInfoRepo;
import nex.vts.backend.repositories.GetExpenseHeaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class DriverInfoController {

    @Autowired
    DriverInfoRepo DriveRepo;

    @Autowired
    ObjectMapper objectMapper;
    //http://localhost:8009/api/private/v1/1/users/1/1/DriverInfo/215
    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/DriverInfo/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> DriverList(@PathVariable("ID") Integer id) throws JsonProcessingException {

       // System.out.println("Mahzabin");

        Optional<DriverInfoModel> GetDriverInfo = DriveRepo.findDriverInfo(id);

        BaseResponse baseResponse = new BaseResponse();


        if (GetDriverInfo.isEmpty()) {
            baseResponse.status = false;
            baseResponse.apiName= "Get Driver Info";
            baseResponse.version= "v.0.0.1";
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        }
        else
        {
            GetDriverInfoObj getDriverInfoObj = new GetDriverInfoObj();
            baseResponse.status = true;
            baseResponse.apiName= "Get Driver Info";
            baseResponse.version= "v.0.0.1";
            getDriverInfoObj.setDriverInfoModels(GetDriverInfo);
            baseResponse.data =  getDriverInfoObj;
        }


        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

}
