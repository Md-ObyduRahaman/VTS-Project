package nex.vts.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.SetVehicleSettingInfo;
import nex.vts.backend.models.responses.VehicleOthersInfoModel;
import nex.vts.backend.models.responses.VehicleSettingResponse;
import nex.vts.backend.repositories.VehicleOthersInfoRepo;
import nex.vts.backend.repositories.VehicleSettingRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;
import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlSetVehicleSetting {
    private final Logger logger = LoggerFactory.getLogger(CtrlSetVehicleSetting.class);
    private SetVehicleSettingInfo reqBody = null;
    @Autowired
    ObjectMapper objectMapper;
    private final short API_VERSION = 1;
    @Autowired
    VehicleSettingRepo vehicleSettingRepo;
    @Autowired
    VehicleOthersInfoRepo vehicleOthersInfoRepo;
    @Autowired
    Environment environment;
    private static VehicleOthersInfoModel permissionData_old;

    @PostMapping(value = "/v1/{userId}/{deviceType}/settings/vehicle-settings/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setVehicleSettings(@RequestHeader("Authorization") String jwtToken, @RequestParam Map<String, String> requestBody, @PathVariable("deviceType") Integer deviceType, @PathVariable(value = "userId") Long userId) throws IOException, SQLException {
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption decryptedValue = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
        Long getUserId = deObfuscateId(userId);
        boolean checkBool = true;
        Integer changeVehicleStatus, changeMaxSpeed, changeEmail, changeSMS;
        String decode_data = decryptedValue.aesDecrypt(requestBody.get("data"), API_VERSION);        /* Input Validation */
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2##" + deviceType + "##" + API_VERSION);
        }
        reqBody = objectMapper.readValue(decode_data, SetVehicleSettingInfo.class);
        String setFev = setFavoriteVehicle(reqBody);
        VehicleOthersInfoModel permisionData_new = reqBody.getVehicleOthersInfoModel();
        Optional<VehicleOthersInfoModel> vehicleOthersInfo = vehicleOthersInfoRepo.getVehicleOthersInfo(reqBody.getVehicleId(), deviceType);
        if (vehicleOthersInfo.isPresent()) {
            permissionData_old = vehicleOthersInfo.get();
        } else {
            System.out.println("list is empty");
            throw new AppCommonException(4009 + "##VEHICLEOTHERSINFO LIST IS EMPTY##" + deviceType + "##" + API_VERSION);
        } /* // Starting fetch data HttpClient httpClient = HttpClients.createDefault(); String apiUrl = "http://localhost:8009/api/private/v1/1/users/252/getVehicleSetting/"+reqBody.getVehicleId(); HttpGet httpGet = new HttpGet(apiUrl); httpGet.setHeader(HttpHeaders.AUTHORIZATION, jwtToken); HttpResponse response = httpClient.execute(httpGet); HttpEntity entity = response.getEntity(); String responseBody = EntityUtils.toString(entity); //End fetching data from API ObjectMapper objectMapper = new ObjectMapper(); JsonNode jsonNode = objectMapper.readTree(responseBody); JsonNode permissionData = jsonNode.get("data"); permisionData_old=new VehicleOthersInfoModel(permissionData.get("ind_PASS").toString().replace("\"", ""), permissionData.get("cellPhone").toString().replace("\"", ""), permissionData.get("emailId").toString().replace("\"", ""), Boolean.parseBoolean(permissionData.get("isFavorite").toString()), Integer.parseInt(permissionData.get("vehicleStatus").toString()), Boolean.parseBoolean(permissionData.get("multipleNotification").toString()), Integer.parseInt(permissionData.get("safeMode").toString()), Integer.parseInt(permissionData.get("maxSpeedValue").toString())); */
        String p_permissionType = "ChangeAll";
        changeVehicleStatus = vehicleSettingRepo.getDiffSettingInfo("ChangeVehicleStatus", reqBody.getProfileType(), reqBody.getProfileId(), reqBody.getParentId(), reqBody.getVehicleId());
        changeMaxSpeed = vehicleSettingRepo.getDiffSettingInfo("ChangeMaxSpeed", reqBody.getProfileType(), reqBody.getProfileId(), reqBody.getParentId(), reqBody.getVehicleId());
        changeEmail = vehicleSettingRepo.getDiffSettingInfo("ChangeEmail", reqBody.getProfileType(), reqBody.getProfileId(), reqBody.getParentId(), reqBody.getVehicleId());
        changeSMS = vehicleSettingRepo.getDiffSettingInfo("ChangeSMS", reqBody.getProfileType(), reqBody.getProfileId(), reqBody.getParentId(), reqBody.getVehicleId());
        if (reqBody.getProfileType() == 1) {
        } else {
            if ((!Objects.equals(permisionData_new.getCELL_PHONE(), permissionData_old.getCELL_PHONE())) && (changeSMS == 0)) {
                checkBool = false;
            }
            if ((!Objects.equals(permisionData_new.getEMAIL(), permissionData_old.getEMAIL())) && (changeEmail == 0)) {
                checkBool = false;
            }
            if ((!Objects.equals(permisionData_new.getMAX_CAR_SPEED(), permissionData_old.getMAX_CAR_SPEED())) && (changeMaxSpeed == 0)) {
                checkBool = false;
            }
        }
        BaseResponse baseResponse = new BaseResponse();
        if (!checkBool) {
            baseResponse.status = false;
            baseResponse.apiName = "changeVehicleStatus";
            baseResponse.version = "V.0.0.1";
            baseResponse.errorMsg = "Operation Failed. You are not permitted to update vehicle settings";
            baseResponse.errorCode = 4005;
        } else {
            String msg = updateVehicleSettingInfo(reqBody, "ChangeAll", "");
            VehicleSettingResponse vehicleSettingResponse = new VehicleSettingResponse(msg);
            baseResponse.status = true;
            baseResponse.apiName = "changeVehicleStatus";
            baseResponse.version = "V.0.0.1";
            baseResponse.data = vehicleSettingResponse;
        }
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }

    public String updateVehicleSettingInfo(SetVehicleSettingInfo request, String p_permissionType, String p_password) {
        String out = null;
        Integer status = null;
        Boolean multi_not = null;
        Integer safe_mode = null;
        if (Objects.equals(p_permissionType, "ChangeAll")) {
            status = permissionData_old.getVEHICLE_STATUS();
            multi_not = permissionData_old.getIS_MULTIPLE_NOTIFICATION_ALLOW();
            safe_mode = permissionData_old.getIS_SAFE_MODE_ACTIVE();
        } else {
            status = request.getVehicleOthersInfoModel().getVEHICLE_STATUS();
            multi_not = request.getVehicleOthersInfoModel().getIS_MULTIPLE_NOTIFICATION_ALLOW();
            safe_mode = request.getVehicleOthersInfoModel().getIS_SAFE_MODE_ACTIVE();
        }
        Integer p_profileType = request.getProfileType(), p_vehicle_id = request.getVehicleId(), p_profileId_new = request.getProfileId(), p_parentId = request.getParentId();
        try {
            out = vehicleSettingRepo.modify_vehicle_profile(p_permissionType, p_profileType, p_profileId_new, p_parentId, p_vehicle_id, status, p_password, request.getVehicleOthersInfoModel().getMAX_CAR_SPEED(), request.getVehicleOthersInfoModel().getCELL_PHONE(), request.getVehicleOthersInfoModel().getEMAIL(), multi_not, safe_mode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public String setFavoriteVehicle(SetVehicleSettingInfo request) throws SQLException {
        String p_type;
        int p_favorite_value;
        if (request.getVehicleOthersInfoModel().getIS_FAVORITE()) {
            p_type = "SetFavorite";
            p_favorite_value = 1;
        } else {
            p_type = "UnsetFavorite";
            p_favorite_value = 0;
        }
        String out = vehicleSettingRepo.manage_favorite_vehicle(p_type, request.getProfileType(), request.getProfileId(), request.getParentId(), request.getVehicleId(), p_favorite_value);
        return out;
    }
}
