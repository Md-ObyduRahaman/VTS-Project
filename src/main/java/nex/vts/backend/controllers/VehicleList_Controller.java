package nex.vts.backend.controllers;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_List_Service;
import nex.vts.backend.services.Vehicle_Location_Service;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleList_Controller {
    private final Logger logger = LoggerFactory.getLogger(VehicleList_Controller.class.getName());
    private final Vehicle_List_Service Vehicle_List_Service;
    private final Vehicle_Location_Service locationService;
    Map<String, Object> respnse = new LinkedHashMap<>();
    BaseResponse baseResponse = new BaseResponse();

    public VehicleList_Controller(Vehicle_List_Service Vehicle_List_Service, Vehicle_Location_Service locationService) {
        this.Vehicle_List_Service = Vehicle_List_Service;
        this.locationService = locationService;

    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "users/{userId}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleList(@RequestHeader(value = "data") String data, @PathVariable(value = "userId") Integer userId) {
        Integer groupId, operatorId;
        String limit;
        Integer offset, userType, parentId;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        groupId = Integer.parseInt(jsonFormat.get("groupId").toString());
        limit = jsonFormat.get("limit").toString();
        offset = Integer.parseInt(jsonFormat.get("offset").toString());
        userType = Integer.parseInt(jsonFormat.get("userType").toString());
        parentId = Integer.parseInt(jsonFormat.get("parentId").toString());
        Object getVehicleInfo = Vehicle_List_Service.getVehicles(groupId, limit, offset, userType, parentId);
        if (!getVehicleInfo.equals(null)) {
            baseResponse.data = getVehicleInfo;
            baseResponse.status = true;
            baseResponse.version = "V.0.0.1";
            baseResponse.apiName = "Vehicle-List";
        } else {
            baseResponse.data = null;
            baseResponse.status = false;
            baseResponse.version = "V.0.0.1";
            baseResponse.apiName = "Vehicle-List";
            baseResponse.errorMsg = "The resource or endpoint was not found";
        }
        return ResponseEntity.ok(baseResponse);
    }


}
