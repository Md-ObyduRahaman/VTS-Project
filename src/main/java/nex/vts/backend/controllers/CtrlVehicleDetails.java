package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleDetailsResponse;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.VehicleDetails_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlVehicleDetails {
    BaseResponse baseResponse = new BaseResponse();
    private final VehicleDetails_Service detailsService;
/*    Map<String, Object> respnse = new LinkedHashMap<>();
    ObjectMapper mapper = new ObjectMapper();*/
    VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
    Environment environment;
    RepoVtsLoginUser repoVtsLoginUser;
    private final short API_VERSION = 1;

    public CtrlVehicleDetails(VehicleDetails_Service detailsService, Environment environment, RepoVtsLoginUser vtsLoginUser) {
        this.detailsService = detailsService;
        this.environment = environment;
        this.repoVtsLoginUser = vtsLoginUser;
    }


//    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
//    @GetMapping(value = "/{deviceType}/user/{userId}/vehicle/details", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getVehicleDetails(@PathVariable(value = "deviceType") Integer deviceType, @PathVariable(value = "userId") Integer userId) throws SQLException, JsonProcessingException {
//
//        String activeProfile = environment.getProperty("spring.profiles.active");
//        AESEncryptionDecryption encryptionDecryption = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String schemaName = environment.getProperty("application.profiles.shcemaName");
//        Integer operatorId = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
//
//        Optional<VTS_LOGIN_USER> vtsLoginUser = repoVtsLoginUser.findByUserName(userDetails.getUsername(),environment.getProperty("application.profiles.shcemaName"));
//
//        if (vtsLoginUser.isPresent())
//            loginUser = vtsLoginUser.get();
//        else
//            throw new AppCommonException(400 + "##login cred not found##" + "##" + API_VERSION);
//
//        Integer profileType = loginUser.getUSER_TYPE();
//        Integer usersIds = Math.toIntExact(deObfuscateId(Long.valueOf(userId)));
//
///*        VehicleDetailsModel detailsModel = mapper.readValue(encryptionDecryption.aesDecrypt(reqbody,API_VERSION),VehicleDetailsModel.class);
//        Integer userType = detailsModel.userType;
//        Integer profileId = detailsModel.profileId;*/
//
////        VehicleDetailsResponse vehicleDetailsResponse = detailsService.getVehicleDetail(profileType,userId,schemaName,operatorId);
//
//        if (!detailsService.getVehicleDetail(profileType,usersIds,schemaName,operatorId).equals(null)) {
//
//            baseResponse.data = detailsService.getVehicleDetail(profileType,usersIds,schemaName,operatorId);
//            baseResponse.apiName = "Vehicle-Details";
//            baseResponse.status = true;
//        }
//        else {
//            baseResponse.data=new ArrayList<>();
//            baseResponse.apiName = "Vehicle-Details";
//            baseResponse.errorMsg = "can not provide required parameter";
//            baseResponse.status = false;
//        }
///*        baseResponse.apiName = "vehicle-Detail";
//        baseResponse.status = true;
//        baseResponse.data = respnse;*/
//        return ResponseEntity.ok(baseResponse);
//    }


}

