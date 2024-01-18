package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.Vehicle_List_Service;
import nex.vts.backend.services.VehicleLocation_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlVehicleList {
    private final Logger logger = LoggerFactory.getLogger(CtrlVehicleList.class.getName());
    private final Vehicle_List_Service Vehicle_List_Service;
    private final VehicleLocation_Service locationService;
    BaseResponse baseResponse = new BaseResponse();
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    Environment environment;
    private final short API_VERSION = 1;

    @Autowired
    public CtrlVehicleList(Vehicle_List_Service Vehicle_List_Service, VehicleLocation_Service locationService, Environment environment) {
        this.Vehicle_List_Service = Vehicle_List_Service;
        this.locationService = locationService;
        this.environment = environment;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/{deviceType}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleList(@PathVariable(value = "deviceType") Integer deviceType/*, @PathVariable(required = false, value = "offset") Integer offset, @PathVariable(required = false, value = "limit") String limit*/) throws JsonProcessingException {

        String activeProfile = environment.getProperty("spring.profiles.active");
        Integer operatorId = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        String schemaName = environment.getProperty("application.profiles.shcemaName");
        AESEncryptionDecryption decryptedValue = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(username, environment.getProperty("application.profiles.shcemaName"));

        if (vtsLoginUserOpt.isPresent())
            loginUser = vtsLoginUserOpt.get();
        else
            throw new AppCommonException(400 + "##login cred not found##" + loginUser.getPROFILE_ID() + "##" + API_VERSION);

        Object getVehicleInfo = Vehicle_List_Service.getVehicles(
                loginUser.getPROFILE_ID(),
                loginUser.getUSER_TYPE(),
                operatorId,
                schemaName,
                Integer.valueOf(loginUser.getPARENT_PROFILE_ID()),
                deviceType,
                API_VERSION);

        if (!getVehicleInfo.equals(null)) {

            baseResponse.data = getVehicleInfo;
            baseResponse.status = true;
            baseResponse.apiName = "Vehicle-List";
        } else {

            baseResponse.data=new ArrayList<>();
            baseResponse.status = false;
            baseResponse.apiName = "Vehicle-List";
            baseResponse.errorMsg = "The resource or endpoint was not found";
        } /*        } else throw new AppCommonException(400 + "##BAD REQUEST 2##" + userId + "##" + API_VERSION); return ResponseEntity.ok(baseResponse);*/
        return ResponseEntity.ok(baseResponse);
    }
}
