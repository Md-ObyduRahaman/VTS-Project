package nex.vts.backend.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.DriverInfoRepo;
import nex.vts.backend.repositories.GetExpenseHeaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/api/private")
public class DriverInfoController {

    @Autowired
    DriverInfoRepo repo;

    @Autowired
    ObjectMapper objectMapper;
    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/DriverInfo/usrID")
    public ResponseEntity<String> vehicleList(@PathVariable("usrID") Integer id) throws JsonProcessingException {


        Optional<DriverInfoModel> GetDriverInfo = repo.findDriverInfo(id);

        BaseResponse baseResponse = new BaseResponse();


        if (GetDriverInfo.isEmpty()) {
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            GetDriverInfoObj getDriverInfoObj = new GetDriverInfoObj();
            baseResponse.status = true;
            GetDriverInfoObj.setGetDriverModels(GetDriverInfo);
            baseResponse.data =  getDriverInfoObj;
        }


        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

}
