package nex.vts.backend.controllers;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_Details_Service;
import nex.vts.backend.services.Vehicle_History_Service;
import nex.vts.backend.services.Vehicle_List_Service;
import nex.vts.backend.services.Vehicle_Location_Service;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/private/v1")
public class Vehicle_Controller {
    private final Logger logger = LoggerFactory.getLogger(Vehicle_Controller.class.getName());
    private final Vehicle_List_Service Vehicle_List_Service;
    private final Vehicle_Details_Service detailsService;
    private final Vehicle_Location_Service locationService;
    private final Vehicle_History_Service historyService;
    Map<String, Object> respnse = new LinkedHashMap<>(), vehicle = new LinkedHashMap<>();
    BaseResponse baseResponse = new BaseResponse();

    public Vehicle_Controller(Vehicle_List_Service Vehicle_List_Service, Vehicle_Details_Service detailsService, Vehicle_Location_Service locationService, Vehicle_History_Service historyService) {
        this.Vehicle_List_Service = Vehicle_List_Service;
        this.detailsService = detailsService;
        this.locationService = locationService;
        this.historyService = historyService;
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
        try {
            if (!(jsonFormat.get("groupId").toString().isEmpty() && jsonFormat.get("operationId").toString().isEmpty() && jsonFormat.get("limit").toString().isEmpty() && jsonFormat.get("offset").toString().isEmpty() && jsonFormat.get("userType").toString().isEmpty() && jsonFormat.get("parentId").toString().isEmpty())) {
                groupId = Integer.parseInt(jsonFormat.get("groupId").toString());
                operatorId = Integer.parseInt(jsonFormat.get("operationId").toString());
                limit = jsonFormat.get("limit").toString();
                offset = Integer.parseInt(jsonFormat.get("offset").toString());
                userType = Integer.parseInt(jsonFormat.get("userType").toString());
                parentId = Integer.parseInt(jsonFormat.get("parentId").toString());
                Object vehicleList = Vehicle_List_Service.getVehicleList(groupId, operatorId, limit, offset, userType, parentId);
                if (userType.equals(1)) {
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
                }
            }
        } catch (Exception e) {
            logger.warn("can not provide appropriate parameter in json", e.getMessage());
        }
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/vehicle/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleDetails(@RequestHeader(value = "data") String data) throws SQLException {
        Integer userType, profileId, vehicleId, parentId;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        userType = Integer.parseInt(jsonFormat.get("userType").toString());
        profileId = Integer.parseInt(jsonFormat.get("profileId").toString());
        vehicleId = Integer.parseInt(jsonFormat.get("vehicleId").toString());
        parentId = Integer.parseInt(jsonFormat.get("parentId").toString());
        respnse.put("vehicle-Permission", detailsService.getVehiclePermission(userType, profileId, parentId, vehicleId));
        respnse.put("vehicle-details", detailsService.getVehicleDetails(userType, profileId, vehicleId));
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
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
    } /*todo --- Vehicle history Api*/

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
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
        baseResponse.data = historyService.getVehicleHistory(vehicleId, fromDate, toDate);
        baseResponse.status = true;
        return ResponseEntity.ok(baseResponse);
    }
}
