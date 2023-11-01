package nex.vts.backend.controllers;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_Details_Service;
import nex.vts.backend.services.Vehicle_History_Service;
import nex.vts.backend.services.Vehicle_List_Service;
import nex.vts.backend.services.Vehicle_Location_Service;
import org.apache.http.HttpStatus;
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
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleList_Controller {
    private final Logger logger = LoggerFactory.getLogger(VehicleList_Controller.class.getName());
    private final Vehicle_List_Service Vehicle_List_Service;
    /*private final Vehicle_Details_Service detailsService;*/
    private final Vehicle_Location_Service locationService;
    /*private final Vehicle_History_Service historyService;*/
    Map<String, Object> respnse = new LinkedHashMap<>();
    BaseResponse baseResponse = new BaseResponse();

    public VehicleList_Controller(Vehicle_List_Service Vehicle_List_Service, /*Vehicle_Details_Service detailsService,*/ Vehicle_Location_Service locationService/*, Vehicle_History_Service historyService*/) {
        this.Vehicle_List_Service = Vehicle_List_Service;
        /*this.detailsService = detailsService;*/
        this.locationService = locationService;
        /*this.historyService = historyService;*/
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
//            Object vehicleList = Vehicle_List_Service.getVehicleList(groupId, limit, offset, userType, parentId);
         Object  getVehicleInfo =  Vehicle_List_Service.getVehicles(groupId,limit,offset,userType,parentId);

/*                if (userType.equals(1)) {
                    respnse.put("total-vehicle", Vehicle_List_Service.get_total_vehicle(groupId, parentId, userType));
                    respnse.put("vehicle-list", vehicleList);
                } else if (userType.equals(2)) {
                    respnse.put("total-vehicle", Vehicle_List_Service.get_total_vehicle(groupId, parentId, userType));
                    respnse.put("vehicle-list", vehicleList);
                } else if (userType.equals(3)) {
                    respnse.put("total-vehicle", Vehicle_List_Service.get_total_vehicle(groupId, parentId, userType));
                    respnse.put("vehicle-list", vehicleList);
                } else {
                    respnse.put("total-vehicle", Vehicle_List_Service.get_total_vehicle(groupId, parentId, userType));
                    respnse.put("vehicle-list", vehicleList);
                }*/
/*        if (respnse.isEmpty()) {
            baseResponse.data = null;
            baseResponse.status = false;
            baseResponse.apiName = "get Vehicle List";
            baseResponse.errorMsg = "kindly provide proper parameter";
        } else {
            baseResponse.status = true;
            baseResponse.data = getVehicleInfo;
            baseResponse.apiName = "get Vehicle List";
            baseResponse.version = "V.0.0.1";
        }*/

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
            baseResponse.errorMsg = String.valueOf(HttpStatus.SC_NOT_FOUND);
        }
        return ResponseEntity.ok(baseResponse);
    }

/*    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/vehicle/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleDetails(@RequestHeader(value = "data") String data) throws SQLException {
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        respnse.put("vehicle-Permission", detailsService.getVehiclePermission(Integer.parseInt(jsonFormat.get("userType").toString()), Integer.parseInt(jsonFormat.get("profileId").toString()), Integer.parseInt(jsonFormat.get("parentId").toString()), Integer.parseInt(jsonFormat.get("vehicleId").toString())));
        respnse.put("vehicle-details", detailsService.getVehicleDetails(Integer.parseInt(jsonFormat.get("userType").toString()), Integer.parseInt(jsonFormat.get("profileId").toString()), Integer.parseInt(jsonFormat.get("vehicleId").toString())));
        baseResponse.apiName = "vehicle-Detail";
        baseResponse.version = "V.0.0.1";
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }*/

/*    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/vehicle/district", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleDistrict() throws SQLException, BadSqlGrammarException, DataAccessException {
        respnse.put("Vehicle-District", locationService.getVehicleDistrict());
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/vehicle/thana", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleThana(@RequestHeader(value = "data") String data) throws SQLException, BadSqlGrammarException, DataAccessException {
        Integer thanaId;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        thanaId = Integer.parseInt(jsonFormat.get("thanaId").toString());
        respnse.put("Vehicle-Thana", locationService.getVehicleThana(thanaId));
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/vehicle/road", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleRoad(@RequestHeader(value = "data") String data) throws SQLException, BadSqlGrammarException, DataAccessException {
        Integer districtId;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        districtId = Integer.parseInt(jsonFormat.get("districtId").toString());
        respnse.put("Vehicle-Thana", locationService.getVehicleRoad(districtId));
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }*/ /*todo --- Vehicle history Api*/

/*    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/vehicle-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleHistory(@RequestHeader(value = "data") String data) {
        Map<String, Object> totalCount = new HashMap<>();
        Integer vehicleId;
        String fromDate, toDate;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        vehicleId = Integer.parseInt(jsonFormat.get("vehicleId").toString());
        fromDate = jsonFormat.get("fromDate").toString();
        toDate = jsonFormat.get("toDate").toString();
        respnse.put("Vehicle-Hisotry",historyService.getVehicleHistory(vehicleId, fromDate, toDate));
        baseResponse.data = respnse;
        baseResponse.apiName = "vehicle-history";
        baseResponse.version = "V.0.0.1";
        baseResponse.status = true;
        return ResponseEntity.ok(baseResponse);
    }*/
}
