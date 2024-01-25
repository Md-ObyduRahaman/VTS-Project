package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehDriverInfo;
import nex.vts.backend.repositories.DriverInfoRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class VehDriverDetailsController {

    private final Logger logger = LoggerFactory.getLogger(VehDriverDetailsController.class);
    private final BaseResponse baseResponse = new BaseResponse();
    private final short API_VERSION = 1;


    @Autowired
    DriverInfoRepo DriveRepo;
    @Autowired
    Environment environment;

    @Autowired
    ObjectMapper objectMapper;


    //http://localhost:8009/api/private/v1/1/users/1/1/DriverProfile/13293

//    @RequestMapping(value = "/api/v1/{deviceType}/users/{userId}/{userType}/DriverProfile/{vehicleId}", produces = "application/json; charset=UTF-8;", method = RequestMethod.GET)
    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/DriverProfile/{vehicleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> Get_DriverInfo(
            @PathVariable("vehicleId") Long vehicleId,
            @PathVariable("userId") Long userId,
            @PathVariable("deviceType") int deviceType,
            @PathVariable("userType") int userType) throws JsonProcessingException {
        //
        //
        String _API_NAME = "GetDriverInfo";
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        userId = deObfuscateId(userId);
        vehicleId = deObfuscateId(vehicleId);
        //
        //
        //-----------FIELD-VALIDATION---------------->>>
        Optional<ArrayList<VehDriverInfo>> Get_DriverInfo = DriveRepo.get_DriverInfo(
                String.valueOf(userId), Math.toIntExact(vehicleId), deviceType, userType
        );
        //-----------FIELD-VALIDATION----------------<<<
        //
        //
        // SET:: RESPONSE DATA --->> /start/
        if (Get_DriverInfo.isEmpty()) {
            baseResponse.data = new ArrayList<>();
            baseResponse.status = false;
            baseResponse.apiName = _API_NAME;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.apiName = _API_NAME;
            baseResponse.data = Get_DriverInfo.flatMap(list -> list.stream().findFirst()).orElse(null); // [Without Array Data]
//            baseResponse.data = Get_DriverInfo;  // [Array Data]
        }
        // SET:: RESPONSE DATA ---<< /end/
        //
        //
        //:: RESPONSE-PRINT ---
        try {
            return new ResponseEntity<>(objectMapper.writeValueAsString(baseResponse), HttpStatus.OK);
        } catch (JsonProcessingException ex) {
            logger.error("Preparing response error: " + ex.getMessage());
        }
        throw new AppCommonException(4001 + "##Invalid response##" + deviceType + "##" + API_VERSION);
        //throw new CustomErrorException("ERR-002.011", "Invalid response!");

        //return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
        // return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse),API_VERSION));

    }


//    BaseResponse baseResponse = new BaseResponse();
//    private final VehicleDetails_Service detailsService;
///*    Map<String, Object> respnse = new LinkedHashMap<>();
//    ObjectMapper mapper = new ObjectMapper();*/
//    VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
//    Environment environment;
//    RepoVtsLoginUser repoVtsLoginUser;
//    private final short API_VERSION = 1;
//
//    public VehDriverDetails_Controller(VehicleDetails_Service detailsService, Environment environment, RepoVtsLoginUser vtsLoginUser) {
//        this.detailsService = detailsService;
//        this.environment = environment;
//        this.repoVtsLoginUser = vtsLoginUser;
//    }
//
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

