package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleConfigModel;
import nex.vts.backend.models.responses.VehicleSettingMapping;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.ModifyVehicleSetting_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class CtrlModifyVehicleSetting {
    private final short API_VERSION = 1;

    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    BaseResponse response = new BaseResponse();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Environment environment;
    private ModifyVehicleSetting_Service vehicleSettingService;

    @Autowired
    public CtrlModifyVehicleSetting(ModifyVehicleSetting_Service vehicleSettingService) {
        this.vehicleSettingService = vehicleSettingService;
    }

    @PostMapping("/v1/{deviceType}/vehicle/{vehicleId}/settings")
    public ResponseEntity<?> modifyVehicleSetting(@PathVariable(value = "deviceType")Integer deviceType,
                                                  @PathVariable(value = "vehicleId")Integer vehicleId,
                                                  @RequestParam(value = "data") String reqBody) throws JsonProcessingException, SQLException {


        String activeProfile = environment.getProperty("spring.profiles.active");
        Integer operatorId = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        String schemaName = environment.getProperty("application.profiles.shcemaName");
        AESEncryptionDecryption encryptionDecryption = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(username, environment.getProperty("application.profiles.shcemaName"));

        if (vtsLoginUserOpt.isPresent())
            loginUser = vtsLoginUserOpt.get();
        else
            throw new AppCommonException(400 + "##login cred not found##" + loginUser.getPROFILE_ID() + "##" + API_VERSION);

        Integer vehicleIds = Math.toIntExact(deObfuscateId(Long.valueOf(vehicleId)));
        String profileId = String.valueOf(loginUser.getPROFILE_ID());
        String parentProfileId = String.valueOf(loginUser.getMAIN_ACCOUNT_ID());
        Integer profileType = loginUser.getUSER_TYPE();

        VehicleSettingMapping vehicleSettingMapping = mapper.readValue(encryptionDecryption.aesDecrypt(reqBody,API_VERSION),VehicleSettingMapping.class);

        String maxSpeed = vehicleSettingMapping.maxSpeed;
        String cellPhone = vehicleSettingMapping.cellPhone;
        String email = vehicleSettingMapping.email;
        int isFavourite = vehicleSettingMapping.isFavourite;


        response.data = vehicleSettingService.modifyVehicleSettingResponse(profileType, Integer.valueOf(profileId),
                Integer.valueOf(parentProfileId), vehicleIds, maxSpeed, cellPhone, email, isFavourite, schemaName);

        response.apiName = "Modify Vehicle Settings";
        response.status = true;


        return ResponseEntity.ok(response);

    }
    // localhost:8009/api/private/v1/1/vehicle/14079978/setting

    @GetMapping("/v1/{deviceType}/vehicle/{vehicleId}/setting")
    public ResponseEntity<?> getVehicleSetting(@PathVariable(value = "deviceType")Integer deviceType,
                                               @PathVariable(value = "vehicleId")Integer vehicleId){

        vehicleId = Math.toIntExact(deObfuscateId(Long.valueOf(vehicleId)));

        VehicleConfigModel configModel =  vehicleSettingService.getVehicleSetting(vehicleId);

        if (!configModel.equals(null)) {

            response.data = configModel;
            response.status = true;
            response.apiName = "Get Vehicle Setting";
        }else {
            response.data = null;
            response.status = false;
            response.errorMsg = "failed to load setting";
        }

       return ResponseEntity.ok(response);
    }
}
