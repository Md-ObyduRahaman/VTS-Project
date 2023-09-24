package nex.vts.backend.controllers;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_Details_Service;
import nex.vts.backend.services.vehicleListService;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/private/v1")
@SuppressWarnings("all")
public class Vehicle_Controller {
    private final vehicleListService vehicleListService;
    private final Vehicle_Details_Service detailsService;
    Map<String, Object> respnse = new LinkedHashMap<>(), vehicle = new LinkedHashMap<>();
    BaseResponse baseResponse = new BaseResponse();

    public Vehicle_Controller(vehicleListService vehicleListService, Vehicle_Details_Service detailsService) {
        this.vehicleListService = vehicleListService;
        this.detailsService = detailsService;
    }

    @GetMapping(value = "/vehicle/list",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleList(@RequestHeader(value = "data") String data) {
        Integer groupId, operationId;
        String limit;
        Integer offset, userType, parentId;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        groupId = Integer.parseInt(jsonFormat.get("groupId").toString());
        operationId = Integer.parseInt(jsonFormat.get("operationId").toString());
        limit = jsonFormat.get("limit").toString();
        offset = Integer.parseInt(jsonFormat.get("offset").toString());
        userType = Integer.parseInt(jsonFormat.get("userType").toString());
        parentId = Integer.parseInt(jsonFormat.get("parentId").toString());
        Optional vehicleList = vehicleListService.getVehicleList(groupId, operationId, limit, offset, userType, parentId);
        if (userType.equals(1)) {
            respnse.put("total-vehicle",vehicleListService.get_total_vehicle(groupId, parentId, userType));
            respnse.put("vehicle-list", vehicleList);
        } else if (userType.equals(2)) {
            respnse.put("total-vehicle", vehicleListService.get_total_vehicle(groupId, parentId, userType));
            respnse.put("vehicle-list", vehicleList);
        } else if (userType.equals(3)) {
            respnse.put("total-vehicle", vehicleListService.get_total_vehicle(groupId, parentId, userType));
            respnse.put("vehicle-list", vehicleList);
        } else {
            respnse.put("total-vehicle", vehicleListService.get_total_vehicle(groupId, parentId, userType));
            respnse.put("vehicle-list", vehicleList);
        }
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    } /*    @GetMapping("/vehicle/list") public ResponseEntity<?> getVehicleList(@RequestParam("groupId") Integer groupId, @RequestParam("operationId") Integer operationId, @RequestParam("limit") String limit, @RequestParam(value = "offset",required = false)Integer offset, @RequestParam(value = "userType")Integer userType, @RequestParam(value = "parentId") Integer parentId ){ return ResponseEntity.ok(vehicleListService.getVehicleList(groupId, operationId,limit,offset,userType,parentId)); }*/

    @GetMapping(value = "/vehicle/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleDetails(@RequestHeader(value = "data") String data) throws SQLException {
        Integer userType, profileId, vehicleId,parentId;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        userType = Integer.parseInt(jsonFormat.get("userType").toString());
        profileId = Integer.parseInt(jsonFormat.get("profileId").toString());
        vehicleId = Integer.parseInt(jsonFormat.get("vehicleId").toString());
        parentId = Integer.parseInt(jsonFormat.get("parentId").toString());
        respnse.put("vehicle-Permission",detailsService.getVehiclePermission(userType,profileId,parentId,vehicleId));
        respnse.put("vehicle-details",detailsService.getVehicleDetails(userType, profileId, vehicleId));
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }

}
