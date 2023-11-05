package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_History_Service;
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
public class VehicleHistory_Controller {
    BaseResponse baseResponse = new BaseResponse();
    Map<String, Object> respnse = new LinkedHashMap<>();
    private final Vehicle_History_Service historyService;
    ObjectMapper objectMapper = new ObjectMapper();

    public VehicleHistory_Controller(Vehicle_History_Service historyService) {
        this.historyService = historyService;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "{deviceType}//vehicle-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleHistory(@RequestHeader(value = "data") String data,@PathVariable("deviceType") Integer deviceType) throws JsonProcessingException {
        byte[] decode_data = Base64.getDecoder().decode(data);
        String string_decode_data = new String(decode_data);
        JsonNode jsonNode = objectMapper.readTree(string_decode_data);
        respnse.put("Vehicle-History", historyService.getVehicleHistory(Integer.parseInt(jsonNode.get("vehicleId").toString()), jsonNode.get("fromDate").toString(), jsonNode.get("toDate").toString()));
        baseResponse.data = respnse;
        baseResponse.apiName = "vehicle-history";
        baseResponse.status = true;
        return ResponseEntity.ok(baseResponse);
    }
}
