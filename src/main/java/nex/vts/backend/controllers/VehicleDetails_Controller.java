package nex.vts.backend.controllers;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_Details_Service;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleDetails_Controller {
    BaseResponse baseResponse = new BaseResponse();
    private final Vehicle_Details_Service detailsService;
    Map<String, Object> respnse = new LinkedHashMap<>();

    public VehicleDetails_Controller(Vehicle_Details_Service detailsService) {
        this.detailsService = detailsService;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
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
    }
}
