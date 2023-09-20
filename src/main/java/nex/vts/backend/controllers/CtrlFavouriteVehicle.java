package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.FavouritVehicleObj;
import nex.vts.backend.models.responses.FavouriteVehiclelModel;
import nex.vts.backend.repositories.FavouriteVehiclelRepo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    ObjectMapper objectMapper;
    private final  Integer LIMIT_MAX_THRESHOLD=20;

    /* /v1/{deviceType}/users/{userId}/favourit-vehicles */
    @GetMapping(value = "/v1/{deviceType}/users/{userId}/favourit-vehicles",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> vehicleList(@RequestHeader("APP_DATA") String appData, @PathVariable("deviceType") Integer deviceType,
                                              @PathVariable("userId") Integer userId) throws JsonProcessingException {

        Integer limit;
        Integer offset,userType,PARENT_PROFILE_ID;

        // Decode the Base64 string
        byte[] decodedBytes = Base64.getDecoder().decode(appData);

        // Convert the decoded bytes to a string
        String decodedString = new String(decodedBytes);

        // Print the decoded string
        System.out.println("Decoded String: " + decodedString);

        JSONObject jsonObject = new JSONObject(decodedString);
        // Print the JSON object
        System.out.println("JSON Object: " + jsonObject);

        userType = Integer.parseInt(jsonObject.get("userType").toString());
        limit = Integer.parseInt(jsonObject.get("limit").toString());
        offset = Integer.parseInt(jsonObject.get("offset").toString());
        PARENT_PROFILE_ID = Integer.parseInt(jsonObject.get("PARENT_PROFILE_ID").toString());

        if (LIMIT_MAX_THRESHOLD<=limit){
            limit=20;
        }

        Optional<ArrayList<FavouriteVehiclelModel>> favouriteVehiclelList= favouriteVehiclelRepo.findNeededData(String.valueOf(limit),offset,userId,1,userType,PARENT_PROFILE_ID);
        BaseResponse baseResponse = new BaseResponse();


        if (favouriteVehiclelList.isEmpty()){
            baseResponse.status = false;
            baseResponse.errorMsg="Data  not found";
            baseResponse.errorCode=4041;
        }else {
            FavouritVehicleObj favouritVehicleObj=new FavouritVehicleObj();
            baseResponse.status = true;
            favouritVehicleObj.setFavouriteVehiclelModels(favouriteVehiclelList);
            baseResponse.data = favouritVehicleObj;
        }

        return  ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }
}
