package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.FavouritVehicleObj;
import nex.vts.backend.models.responses.FavouriteVehiclelModel;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.FavouriteVehiclelRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.json.JSONObject;
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
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class CtrlFavouriteVehicle {


    private final Logger logger = LoggerFactory.getLogger(CtrlFavouriteVehicle.class);

    @Autowired
    FavouriteVehiclelRepo favouriteVehiclelRepo;

    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    private final short API_VERSION = 1;

    @Autowired
    Environment environment;

    @Autowired
    ObjectMapper objectMapper;
    private final  Integer LIMIT_MAX_THRESHOLD=20;

    //http://localhost:8009/api/private/v1/1/users/252/1/favourit-vehicles/50/0
    @GetMapping(value = "/v1/{deviceType}/users/{userId}/favourit-vehicles/{limit}/{offset}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> vehicleList( @PathVariable("deviceType") Integer deviceType,
                                              @PathVariable("userId") Integer userId,@PathVariable("offset") Integer offset,@PathVariable("limit") Integer limit
    ) throws JsonProcessingException {

        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username: " + username);
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUser = repoVtsLoginUser.findByUserName(username);

        if (LIMIT_MAX_THRESHOLD<=limit){
            limit=20;
        }

        Optional<ArrayList<FavouriteVehiclelModel>> favouriteVehicleList= favouriteVehiclelRepo.findNeededData(String.valueOf(limit),offset,userId, 1,vtsLoginUser.get().getUSER_TYPE(), vtsLoginUser.get().getPROFILE_ID(),deviceType);
        BaseResponse baseResponse = new BaseResponse();


        if (favouriteVehicleList.isEmpty()){
            baseResponse.status = false;
            baseResponse.errorMsg="Data  not found";
            baseResponse.errorCode=4041;
        }else {
            FavouritVehicleObj favouritVehicleObj=new FavouritVehicleObj();
            baseResponse.status = true;
            favouritVehicleObj.setFavouriteVehiclelModels(favouriteVehicleList);
            baseResponse.data = favouritVehicleObj;
        }
        System.out.println(ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse)));

        //  return  ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
        return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse),API_VERSION));

    }
}
