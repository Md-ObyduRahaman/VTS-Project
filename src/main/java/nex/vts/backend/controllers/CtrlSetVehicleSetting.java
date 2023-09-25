package nex.vts.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.SetVehicleSettingInfo;

import nex.vts.backend.models.responses.VehicleOthersInfoModel;
import nex.vts.backend.repositories.VehicleSettingRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlSetVehicleSetting {

    private final Logger logger = LoggerFactory.getLogger(CtrlSetVehicleSetting.class);

    private SetVehicleSettingInfo reqBody = null;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    VehicleSettingRepo vehicleSettingRepo;


    @PostMapping(value = "/v1/{deviceType}/users/{userId}/setVehicleSetting/{rowID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setVehicleSettings(@RequestHeader("Authorization") String jwtToken,@PathVariable("rowID") Integer rowID, @RequestParam Map<String, String> requestBody) throws IOException {

        Boolean checkBool = true;
        Integer changeVehicleStatus,changeMaxSpeed,changeEmail,changeSMS;

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }
        reqBody = objectMapper.readValue(requestBody.get("data"), SetVehicleSettingInfo.class);

        VehicleOthersInfoModel permisionData_new= reqBody.getVehicleOthersInfoModel();



        // Starting fetch data
        HttpClient httpClient = HttpClients.createDefault();
        String apiUrl = "http://localhost:8009/api/private/v1/1/users/252/getVehicleSetting/"+rowID;
        HttpGet httpGet = new HttpGet(apiUrl);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        //End fetching data from API

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        JsonNode permissionData = jsonNode.get("data");


        VehicleOthersInfoModel permisionData_old=new VehicleOthersInfoModel(permissionData.get("ind_PASS").toString().replace("\"", ""),
                permissionData.get("cell_PHONE").toString().replace("\"", ""), permissionData.get("email").toString().replace("\"", ""),
                Integer.parseInt(permissionData.get("is_FAVORITE").toString()),
                Integer.parseInt(permissionData.get("vehicle_STATUS").toString()),
                Integer.parseInt(permissionData.get("is_MULTIPLE_NOTIFICATION_ALLOW").toString()),
                Integer.parseInt(permissionData.get("is_SAFE_MODE_ACTIVE").toString()),
                Integer.parseInt(permissionData.get("max_CAR_SPEED").toString()));


        String p_permissionType = "ChangeAll";
        changeVehicleStatus=vehicleSettingRepo.getDiffSettingInfo("ChangeVehicleStatus",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());
        changeMaxSpeed=vehicleSettingRepo.getDiffSettingInfo("ChangeMaxSpeed",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());
        changeEmail=vehicleSettingRepo.getDiffSettingInfo("ChangeEmail",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());
        changeSMS=vehicleSettingRepo.getDiffSettingInfo("ChangeSMS",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());

        vehicleSettingRepo.getModifyVicleProfileResponse("2",2,2,2,2,2,"2","2","2","2",2,2);

        if(reqBody.getProfileType()==1){
            checkBool=true;
        } else {
            if((permisionData_new.getCELL_PHONE() != permisionData_old.getCELL_PHONE()) && (changeSMS == 0)){
                checkBool = false;
            }
            if((permisionData_new.getEMAIL() != permisionData_old.getEMAIL()) && (changeEmail == 0)){
                checkBool = false;
            }
            if((permisionData_new.getMAX_CAR_SPEED() != permisionData_old.getMAX_CAR_SPEED()) && (changeMaxSpeed == 0)){
                checkBool = false;
            }
        }

        BaseResponse baseResponse = new BaseResponse();

        if (!checkBool){
            baseResponse.status = false;
            baseResponse.errorMsg="Operation Failed. You are not permitted to update vehicle settings";
            baseResponse.errorCode=4005;

        }
        else{

           // return updateVehicleSettingInfo($request, $p_permissionType, $p_password);
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));


    }

    public ResponseEntity<String>  updateVehicleSettingInfo(SetVehicleSettingInfo reqBody, String p_permissionType,String p_password)
    {
        return  null;
    }
}
