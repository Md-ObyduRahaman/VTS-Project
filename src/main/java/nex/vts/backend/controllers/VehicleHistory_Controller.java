package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_History_Service;
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
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleHistory_Controller {
    BaseResponse baseResponse = new BaseResponse();
    Map<String, Object> respnse = new LinkedHashMap<>();
    private final Vehicle_History_Service historyService;
    ObjectMapper objectMapper = new ObjectMapper();
    Environment environment;
    private final short API_VERSION = 1;

    public VehicleHistory_Controller(Vehicle_History_Service historyService,Environment environment) {
        this.historyService = historyService;
        this.environment = environment;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "{userId}/{deviceType}/vehicle-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleHistory(@RequestHeader(value = "data") String data,@PathVariable("deviceType") Integer deviceType,@PathVariable(value = "userId")Long userId) throws JsonProcessingException {
        Long getUserId = deObfuscateId(userId);
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption decryptedValue= new AESEncryptionDecryption(activeProfile,deviceType,API_VERSION);
//        byte[] decode_data = Base64.getDecoder().decode(data);
//        String string_decode_data = new String(decode_data);
        String string_decode_data = decryptedValue.aesDecrypt(data,API_VERSION);
        JsonNode jsonNode = objectMapper.readTree(string_decode_data);
        respnse.put("Vehicle-History", historyService.getVehicleHistory(Integer.parseInt(jsonNode.get("vehicleId").toString()), jsonNode.get("fromDate").toString(), jsonNode.get("toDate").toString()));
        baseResponse.data = respnse;
        baseResponse.apiName = "vehicle-history";
        baseResponse.status = true;
        return ResponseEntity.ok(baseResponse);
    }
}
