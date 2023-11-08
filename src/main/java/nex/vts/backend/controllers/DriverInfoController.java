package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.DriverInfoModel;
import nex.vts.backend.models.responses.GetDriverInfoObj;
import nex.vts.backend.repoImpl.DriverInfoImpl;
import nex.vts.backend.repositories.DriverInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class DriverInfoController {

    @Autowired
    private DriverInfoRepo driverInfoRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/DriverInfo/{ID}")
    // http://localhost:8009/api/private/v1/1/users/1/1/DriverInfo/215

    public ResponseEntity<String> vehicleList(@PathVariable("ID") Integer ID) throws JsonProcessingException {

        Optional<DriverInfoModel> getDriverInfo = driverInfoRepo.findDriverInfo(ID);

        BaseResponse baseResponse = new BaseResponse();

        if (getDriverInfo.isEmpty())
        {
            baseResponse.status = false;
            baseResponse.errorMsg = "Data not found";
            baseResponse.errorCode = 4041;
            baseResponse.apiName = "Driver Info";
            baseResponse.version = "v.0.0.1";
        }
        else
        {

             GetDriverInfoObj getDriverInfoObj = new GetDriverInfoObj();
             baseResponse.status = true;
             baseResponse.apiName = "Driver Info";
             baseResponse.version = "v.0.0.1";
             getDriverInfoObj.setGetDriverModels(getDriverInfo);
             baseResponse.data = getDriverInfoObj;

        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }
}
