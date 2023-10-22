package nex.vts.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.*;

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
import java.sql.SQLException;
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

    private  static  VehicleOthersInfoModel permisionData_old;


    @PostMapping(value = "/v1/settings/vehicle-settings/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setVehicleSettings(@RequestHeader("Authorization") String jwtToken, @RequestParam Map<String, String> requestBody) throws IOException, SQLException {

        Boolean checkBool = true;
        Integer changeVehicleStatus,changeMaxSpeed,changeEmail,changeSMS;

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }
        reqBody = objectMapper.readValue(requestBody.get("data"), SetVehicleSettingInfo.class);


        String setFev=setFavoriteVehicle(reqBody);


        VehicleOthersInfoModel permisionData_new= reqBody.getVehicleOthersInfoModel();



        // Starting fetch data
        HttpClient httpClient = HttpClients.createDefault();
        String apiUrl = "http://localhost:8009/api/private/v1/1/users/252/getVehicleSetting/"+reqBody.getVehicleId();
        HttpGet httpGet = new HttpGet(apiUrl);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        //End fetching data from API

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        JsonNode permissionData = jsonNode.get("data");


         permisionData_old=new VehicleOthersInfoModel(permissionData.get("ind_PASS").toString().replace("\"", ""),
                permissionData.get("cellPhone").toString().replace("\"", ""), permissionData.get("emailId").toString().replace("\"", ""),
                Boolean.parseBoolean(permissionData.get("isFavorite").toString()),
                Integer.parseInt(permissionData.get("vehicleStatus").toString()),
                Boolean.parseBoolean(permissionData.get("multipleNotification").toString()),
                Integer.parseInt(permissionData.get("safeMode").toString()),
                Integer.parseInt(permissionData.get("maxSpeedValue").toString()));


        String p_permissionType = "ChangeAll";
        changeVehicleStatus=vehicleSettingRepo.getDiffSettingInfo("ChangeVehicleStatus",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());
        changeMaxSpeed=vehicleSettingRepo.getDiffSettingInfo("ChangeMaxSpeed",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());
        changeEmail=vehicleSettingRepo.getDiffSettingInfo("ChangeEmail",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());
        changeSMS=vehicleSettingRepo.getDiffSettingInfo("ChangeSMS",reqBody.getProfileType(),reqBody.getProfileId(),reqBody.getParentId(),reqBody.getVehicleId());


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
            baseResponse.apiName="changeVehicleStatus";
            baseResponse.version="V.0.0.1";
            baseResponse.errorMsg="Operation Failed. You are not permitted to update vehicle settings";
            baseResponse.errorCode=4005;

        }
        else{
           String msg= updateVehicleSettingInfo(reqBody,"ChangeAll","");
            VehicleSettingResponse vehicleSettingResponse=new VehicleSettingResponse(msg);
            baseResponse.status = true;
            baseResponse.apiName="changeVehicleStatus";
            baseResponse.version="V.0.0.1";
            baseResponse.data = vehicleSettingResponse;
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));


    }

    public String  updateVehicleSettingInfo(SetVehicleSettingInfo request, String p_permissionType,String p_password)
    {
       String out=null;
        Integer status=null;
        Boolean multi_noti=null;
        Integer safe_mode=null;
        if (p_permissionType == "ChangeAll") {
             status = permisionData_old.getVEHICLE_STATUS();
             multi_noti = permisionData_old.getIS_MULTIPLE_NOTIFICATION_ALLOW();
             safe_mode = permisionData_old.getIS_SAFE_MODE_ACTIVE();
        }else {
            status= request.getVehicleOthersInfoModel().getVEHICLE_STATUS();
            multi_noti= request.getVehicleOthersInfoModel().getIS_MULTIPLE_NOTIFICATION_ALLOW();
            safe_mode=request.getVehicleOthersInfoModel().getIS_SAFE_MODE_ACTIVE();
        }

        Integer p_profileType=request.getProfileType();
        Integer p_vehicle_id = request.getVehicleId();
        Integer p_profileId_new = request.getProfileId();
        Integer p_parentId = request.getParentId();


        try {
            out=vehicleSettingRepo.modify_vehicle_profile(p_permissionType,p_profileType,p_profileId_new,p_parentId,
                    p_vehicle_id,status,p_password,request.getVehicleOthersInfoModel().getMAX_CAR_SPEED(),request.getVehicleOthersInfoModel().getCELL_PHONE(),
                    request.getVehicleOthersInfoModel().getEMAIL(),multi_noti,safe_mode);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return  out;
    }


    public String setFavoriteVehicle(SetVehicleSettingInfo request) throws SQLException {

        String p_type;
        Integer p_favorite_value;

        if (request.getVehicleOthersInfoModel().getIS_FAVORITE()) {
            p_type = "SetFavorite";
            p_favorite_value = 1;
        } else {
            p_type = "UnsetFavorite";
            p_favorite_value = 0;
        }

       String out=vehicleSettingRepo.manage_favorite_vehicle(p_type,request.getProfileType(),request.getProfileId(),request.getParentId(),
                request.getVehicleId(),p_favorite_value);

        return out;
    }
}
