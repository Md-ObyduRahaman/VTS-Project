package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleConfigJsonModel;
import nex.vts.backend.models.responses.VehicleConfigModel;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.VehicleConfig_Repo;
import nex.vts.backend.services.VehicleConfig_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlVehicleConfig {

    Logger logger = LoggerFactory.getLogger(CtrlVehicleConfig.class);
    private final short API_VERSION = 1;
    BaseResponse response = new BaseResponse();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public Environment environment;

    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;

    private final VehicleConfig_Service vehicleConfigService;

    @Autowired
    public CtrlVehicleConfig(VehicleConfig_Service vehicleConfigService) {
        this.vehicleConfigService = vehicleConfigService;
    }

    @PostMapping("/{deviceType}/vehicle/{vehicleId}/settings")
    public ResponseEntity<?> setVehicleSettings(@PathVariable(value = "deviceType")Integer deviceType,
                                                @RequestParam(value = "data")String reqBody,
                                                @PathVariable(value = "vehicleId") Integer vehicleId) throws JsonProcessingException {

        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption encryptionDecryption = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(username, environment.getProperty("application.profiles.shcemaName"));

        if (vtsLoginUserOpt.isPresent())
            loginUser = vtsLoginUserOpt.get();
        else
            throw new AppCommonException(400 + "##login cred not found##" + loginUser.getPROFILE_ID() + "##" + API_VERSION);

        VehicleConfigJsonModel vehicleConfigModel = mapper.readValue(encryptionDecryption
                .aesDecrypt(reqBody,API_VERSION), VehicleConfigJsonModel.class);

        String cellPhone = vehicleConfigModel.getCellPhone();
        String email = vehicleConfigModel.getEmail();
        String maxCarSpeed = vehicleConfigModel.getMaxCarSpeed();
        int isFavourite = vehicleConfigModel.getIsFavourite();

        vehicleConfigService.setVehicleSettings(cellPhone,email,maxCarSpeed,isFavourite,vehicleId);




    return null;
    }
}
