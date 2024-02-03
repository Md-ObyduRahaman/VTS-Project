package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleStateInfoOra;
import nex.vts.backend.models.responses.VehicleStateReport;
import nex.vts.backend.repositories.VehicleStateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class CtrlVehicleState {

    private final Logger logger = LoggerFactory.getLogger(CtrlVehicleState.class);
    private final short API_VERSION = 1;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    VehicleStateRepo vehicleStateRepo;
    //localhost:8009/api/private/v1/1/users/2/18/getVehicleStateInfo/7215/14505/0
    //localhost:8009/api/private/v1/1/users/2/2/localhost:8009/api/private/v1/1/users/2/2/vehicle_state/4035/0/0/4035/0/0

    @GetMapping(value = "/v1/{deviceType}/users/{userType}/{userId}/vehicle_state/{mainAccountId}/{SPECIFIC_VEHICLE_ID}/{offSet}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getVehicleStateInfo(@PathVariable("deviceType") Integer deviceType, @PathVariable("SPECIFIC_VEHICLE_ID") String SPECIFIC_VEHICLE_ID, @PathVariable("offSet") Integer offSet,@PathVariable("userId") Integer userId, @PathVariable("userType") Integer userType, @PathVariable("mainAccountId") Integer mainAccountId) throws JsonProcessingException {

        BaseResponse baseResponse = new BaseResponse();
        //userId ofFuscade
        Integer flag= Integer.valueOf(SPECIFIC_VEHICLE_ID);


        if (SPECIFIC_VEHICLE_ID.equals("0"))
        {
            SPECIFIC_VEHICLE_ID=null;
        }
        if (offSet<0)
        {
            offSet=0;
        }

        int count=1;
        VehicleStateReport vehicleStateReport=new VehicleStateReport();

        if(flag==0) {
            count = vehicleStateRepo.findTotalNumber(mainAccountId, userType, userId, SPECIFIC_VEHICLE_ID, offSet);
        }

        Optional<ArrayList<VehicleStateInfoOra>> vehicleStateInfo = vehicleStateRepo.findVehicleStateInfoInfo(mainAccountId, userType, userId, SPECIFIC_VEHICLE_ID, offSet);

        vehicleStateReport.setVehicleStateInfo(vehicleStateInfo);
        vehicleStateReport.setNumberOfRecord(count);



        if (vehicleStateInfo.get().isEmpty()) {
            baseResponse.data=new ArrayList<>();
            baseResponse.status = false;
            baseResponse.apiName= "Get vehicleStateInfo Info";
            baseResponse.errorMsg = "Data not found";
            baseResponse.errorCode = 4041;
        }
        else
        {
            baseResponse.status = true;
            baseResponse.apiName= "vehicleStateInfo";
            baseResponse.data =  vehicleStateReport;
        }


        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

}
