package nex.vts.backend.controllers;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.Vehicle_Location_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/private")
public class VehicleLocation_Controller {
    private final Vehicle_Location_Service locationService;

    @Autowired
    Environment environment;
    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    private final short API_VERSION = 1;
    BaseResponse baseResponse = new BaseResponse();



    public VehicleLocation_Controller(Vehicle_Location_Service locationService) {
        this.locationService = locationService;
    }


    @GetMapping("/v1/{deviceType}/vehicle/location")
    public ResponseEntity<?> getVehicleLocation(/*@RequestHeader(value = "data") Integer vehicleId*//*, @RequestParam("userId") Long userId,@RequestParam("deviceType")Integer deviceType*/@PathVariable(value = "deviceType")Integer deviceType) throws SQLException {
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption decryptedValue = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUser = repoVtsLoginUser.findByUserName(userDetails.getUsername(),environment.getProperty("application.profiles.shcemaName"));
        if (vtsLoginUser.isPresent()) loginUser = vtsLoginUser.get();
        else throw new AppCommonException(400 + "##login cred not found##" + "##" + API_VERSION);
        baseResponse.data = locationService.getVehicleLocationDetails(loginUser.getPROFILE_ID());
        baseResponse.status = true;
        baseResponse.apiName = "Vehicle location";
        return ResponseEntity.ok(baseResponse);
    }

}
