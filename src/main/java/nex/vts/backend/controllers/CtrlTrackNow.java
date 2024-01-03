package nex.vts.backend.controllers;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.TrackHistoryService;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlTrackNow {

    Logger logger = LoggerFactory.getLogger(CtrlTrackNow.class);
    private TrackHistoryService trackHistoryService;
    RepoVtsLoginUser repoVtsLoginUser ;

    @Autowired
    Environment environment;
    BaseResponse baseResponse = new BaseResponse();
    VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
    private final short API_VERSION = 1;

    @Autowired
    public CtrlTrackNow(TrackHistoryService trackHistoryService,RepoVtsLoginUser repoVtsLoginUser) {
        this.trackHistoryService = trackHistoryService;
        this.repoVtsLoginUser = repoVtsLoginUser;
    }


    @GetMapping(value = "/{deviceType}/vehicle/{vehicleId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSingleTrack(@PathVariable("deviceType")Integer deviceType,
                                            @PathVariable("vehicleId")Integer vehicleId){

        Integer vehicleIds = Math.toIntExact(deObfuscateId(Long.valueOf(vehicleId)));
        String activeProfile = environment.getProperty("spring.profiles.active");
        Integer operatorId = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        AESEncryptionDecryption decryptedValue = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser
                .findByUserName(username, environment.getProperty("application.profiles.shcemaName"));

        if (vtsLoginUserOpt.isPresent())
            loginUser = vtsLoginUserOpt.get();
        else
            throw new AppCommonException(400 + "##login cred not found##" + loginUser.getPROFILE_ID() + "##" + API_VERSION);

        Optional<Object> singleTrack = Optional.ofNullable(trackHistoryService.getVehiclePresentLocation(vehicleIds, operatorId));

        if (singleTrack.isPresent()){

            baseResponse.data = trackHistoryService.getVehiclePresentLocation(vehicleIds,operatorId);
            baseResponse.apiName = "Track Now";
            baseResponse.status = true;
        }else {
            baseResponse.data=new ArrayList<>();
            baseResponse.status = false;
            baseResponse.errorCode = 405;
            baseResponse.errorMsg = "No Data found";
        }

        return ResponseEntity.ok(baseResponse);
    }

}
