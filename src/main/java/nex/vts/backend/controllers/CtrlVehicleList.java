package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleList;
import nex.vts.backend.models.responses.VehiclelistItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class CtrlVehicleList {

    private final Logger logger = LoggerFactory.getLogger(CtrlVehicleList.class);

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> vehicleList(@PathVariable("deviceType") Integer deviceType, @PathVariable("userId") long userId) throws JsonProcessingException {

        System.out.println("### Deobfuscated UserId: " + deObfuscateId(userId));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username: " + username);
        //System.out.println("password: " + userDetails.getPassword());

        VehicleList vehicleObject = new VehicleList();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.status = true;

        ArrayList<VehiclelistItem> vehiclelistItems = new ArrayList<>();
        VehiclelistItem vehiclelistItems1 = new VehiclelistItem("Toyota", "Blue", "16479", "Active", "Z605");
        VehiclelistItem vehiclelistItems2 = new VehiclelistItem("BMW", "Black", "452466", "In-Active", "L457");

        vehiclelistItems.add(vehiclelistItems1);
        vehiclelistItems.add(vehiclelistItems2);

        vehicleObject.setVehiclelist(vehiclelistItems);

        baseResponse.data = vehicleObject;
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

}
