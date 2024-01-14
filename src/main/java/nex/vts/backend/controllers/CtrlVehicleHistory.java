package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleHistoryResponse;
import nex.vts.backend.services.VehicleHistory_Service;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlVehicleHistory {
    BaseResponse baseResponse = new BaseResponse();
    private final VehicleHistory_Service historyService;
    ObjectMapper objectMapper = new ObjectMapper();
    Environment environment;
    private final short API_VERSION = 1;

    public CtrlVehicleHistory(VehicleHistory_Service historyService, Environment environment) {
        this.historyService = historyService;
        this.environment = environment;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "/{deviceType}/user/vehicle/{vehicleId}/{fromDateTime}/{toDateTime}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleHistory(@PathVariable("deviceType") Integer deviceType,
                                               @PathVariable("vehicleId")Integer vehicleId,
                                               @PathVariable("fromDateTime")String fromDateTime,
                                               @PathVariable("toDateTime")String toDateTime) throws JsonProcessingException {

        String activeProfile = environment.getProperty("spring.profiles.active");
        Integer operatorId = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        String schemaName = environment.getProperty("application.profiles.shcemaName");
        AESEncryptionDecryption encryptionDecryption = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        Integer vehicleIds = Math.toIntExact(deObfuscateId(Long.valueOf(vehicleId)));
//        Long    fromDateTimes = deObfuscateId(fromDateTime);
//        Long    toDateTimes = deObfuscateId(toDateTime);

        VehicleHistoryResponse historyResponse = historyService.getVehicleHistory(vehicleIds,String.valueOf(fromDateTime),String.valueOf(toDateTime),schemaName,operatorId);

        if (!historyResponse.getHistory().equals(null)){

            baseResponse.data = historyResponse;
            baseResponse.apiName = "Vehicle History";
            baseResponse.status = true;
        }else {
            baseResponse.data=new ArrayList<>();
            baseResponse.apiName = "Vehicle History";
            baseResponse.status = true;
            baseResponse.errorCode = 010;
            baseResponse.errorMsg = "list is empty";
        }

        return ResponseEntity.ok(baseResponse);
    }
}
