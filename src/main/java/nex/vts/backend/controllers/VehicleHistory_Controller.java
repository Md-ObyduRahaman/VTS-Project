package nex.vts.backend.controllers;

import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_History_Service;
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
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleHistory_Controller {
    BaseResponse baseResponse = new BaseResponse();
    Map<String, Object> respnse = new LinkedHashMap<>();
    private final Vehicle_History_Service historyService;

    public VehicleHistory_Controller(Vehicle_History_Service historyService) {
        this.historyService = historyService;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/vehicle-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleHistory(@RequestHeader(value = "data") String data) {
        Integer vehicleId;
        String fromDate, toDate;
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JSONObject jsonFormat = new JSONObject(string_decode_data);
        vehicleId = Integer.parseInt(jsonFormat.get("vehicleId").toString());
        fromDate = jsonFormat.get("fromDate").toString();
        toDate = jsonFormat.get("toDate").toString();
        respnse.put("Vehicle-History", historyService.getVehicleHistory(vehicleId, fromDate, toDate));
        baseResponse.data = respnse;
        baseResponse.apiName = "vehicle-history";
        baseResponse.version = "V.0.0.1";
        baseResponse.status = true;
        return ResponseEntity.ok(baseResponse);
    }
}
