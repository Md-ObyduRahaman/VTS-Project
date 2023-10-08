package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.responses.ManageFavoriteVehicle;
import nex.vts.backend.repositories.SetManageFavoriteVehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlManageFavoriteVehicle {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private SetManageFavoriteVehicleRepo setManageFavoriteVehicleRepo;
    private ManageFavoriteVehicle reqBody = null;

    @PostMapping(value = "/v1/{deviceType}/users/{userId}/manageFavoriteVehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public String setManageFavoriteVehicle(@RequestParam Map<String, String> requestBody) throws JsonProcessingException {

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }
        reqBody = objectMapper.readValue(requestBody.get("data"), ManageFavoriteVehicle.class);
        return setManageFavoriteVehicleRepo.setManageFavoriteVehicle(reqBody);
    }



}
