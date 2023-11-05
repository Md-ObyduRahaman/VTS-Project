package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_List_Service;
import nex.vts.backend.services.Vehicle_Location_Service;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleList_Controller {
    private final Logger logger = LoggerFactory.getLogger(VehicleList_Controller.class.getName());
    private final Vehicle_List_Service Vehicle_List_Service;
    private final Vehicle_Location_Service locationService;
    BaseResponse baseResponse = new BaseResponse();
    ObjectMapper objectMapper = new ObjectMapper();
    private final short API_VERSION = 1;

    public VehicleList_Controller(Vehicle_List_Service Vehicle_List_Service, Vehicle_Location_Service locationService) {
        this.Vehicle_List_Service = Vehicle_List_Service;
        this.locationService = locationService;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "{deviceType}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleList(@RequestHeader(value = "data") String data, @PathVariable(value = "deviceType") Integer userId) throws JsonProcessingException {
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        if (!isNullOrEmpty(string_decode_data)) {
            JsonNode jsonNode = objectMapper.readTree(string_decode_data);
            Object getVehicleInfo =
                    Vehicle_List_Service.getVehicles(
                              Integer.parseInt(jsonNode.get("groupId").toString())
                            , jsonNode.get("limit").toString()
                            , Integer.parseInt(jsonNode.get("offset").toString())
                            , Integer.parseInt(jsonNode.get("userType").toString())
                            , Integer.parseInt(jsonNode.get("parentId").toString()));
            if (!getVehicleInfo.equals(null)) {
                baseResponse.data = getVehicleInfo;
                baseResponse.status = true;
                baseResponse.apiName = "Vehicle-List";
            } else {
                baseResponse.data = null;
                baseResponse.status = false;
                baseResponse.apiName = "Vehicle-List";
                baseResponse.errorMsg = "The resource or endpoint was not found";
            }
        } else throw new AppCommonException(400 + "##BAD REQUEST 2##" + userId + "##" + API_VERSION);
        return ResponseEntity.ok(baseResponse);
    }
}
