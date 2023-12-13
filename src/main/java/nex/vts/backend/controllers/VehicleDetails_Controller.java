package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleDetailsModel;
import nex.vts.backend.models.responses.VehicleDetailsResponse;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.Vehicle_Details_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleDetails_Controller {
    BaseResponse baseResponse = new BaseResponse();
    private final Vehicle_Details_Service detailsService;
    Map<String, Object> respnse = new LinkedHashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
    Environment environment;
    RepoVtsLoginUser repoVtsLoginUser;
    private final short API_VERSION = 1;

    public VehicleDetails_Controller(Vehicle_Details_Service detailsService, Environment environment, RepoVtsLoginUser vtsLoginUser) {
        this.detailsService = detailsService;
        this.environment = environment;
        this.repoVtsLoginUser = vtsLoginUser;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/{deviceType}/vehicle/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleDetails(@PathVariable("deviceType") Integer deviceType, @RequestParam(value = "data")String reqbody) throws SQLException, JsonProcessingException {

        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption encryptionDecryption = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String schemaName = environment.getProperty("application.profiles.shcemaName");
        Optional<VTS_LOGIN_USER> vtsLoginUser = repoVtsLoginUser.findByUserName(userDetails.getUsername(),environment.getProperty("application.profiles.shcemaName"));

        if (vtsLoginUser.isPresent())
            loginUser = vtsLoginUser.get();
        else
            throw new AppCommonException(400 + "##login cred not found##" + "##" + API_VERSION);

        VehicleDetailsModel detailsModel = mapper.readValue(encryptionDecryption.aesDecrypt(reqbody,API_VERSION),VehicleDetailsModel.class);
        Integer userType = detailsModel.userType;
        Integer profileId = detailsModel.profileId;

        VehicleDetailsResponse vehicleDetailsResponse = detailsService.getVehicleDetails(userType,profileId,schemaName);

        if (!vehicleDetailsResponse.equals(null)) {
            baseResponse.data = vehicleDetailsResponse;
            baseResponse.apiName = "Vehicle-Details";
            baseResponse.status = true;
        }
        else {
            baseResponse.apiName = "Vehicle-Details";
            baseResponse.errorMsg = "can not provide required parameter";
            baseResponse.status = false;
        }
/*        baseResponse.apiName = "vehicle-Detail";
        baseResponse.status = true;
        baseResponse.data = respnse;*/
        return ResponseEntity.ok(baseResponse);
    }
}

