package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.Vehicle_Details_Service;
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
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private/v1")
public class VehicleDetails_Controller {
    BaseResponse baseResponse = new BaseResponse();
    private final Vehicle_Details_Service detailsService;
    Map<String, Object> respnse = new LinkedHashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();
    Environment environment;
    private final short API_VERSION = 1;

    public VehicleDetails_Controller(Vehicle_Details_Service detailsService, ObjectMapper objectMapper,Environment environment) {
        this.detailsService = detailsService;
        this.objectMapper = objectMapper;
        this.environment = environment;
    }

    @Retryable(retryFor = {ConnectException.class, DataAccessException.class, ServiceUnavailableException.class}, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
    @GetMapping(value = "{userId}/{deviceType}/vehicle/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleDetails(@RequestHeader(value = "data") String data,@PathVariable("deviceType") Integer deviceType,@PathVariable(value = "userId")Long userId) throws SQLException, JsonProcessingException {
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption decryptedValue= new AESEncryptionDecryption(activeProfile,deviceType,API_VERSION);
        Long getUserId = deObfuscateId(userId);
//        byte[] decode_data = Base64.getDecoder().decode(data);
//        String string_decode_data = new String(decode_data);
        String string_decode_data = decryptedValue.aesDecrypt(data,API_VERSION);
        JsonNode jsonNode = objectMapper.readTree(string_decode_data);
        respnse.put("vehicle-Permission", detailsService.getVehiclePermission(Integer.parseInt(jsonNode.get("userType").toString()), Integer.parseInt(jsonNode.get("profileId").toString()), Integer.parseInt(jsonNode.get("parentId").toString()), Integer.parseInt(jsonNode.get("vehicleId").toString())));
        respnse.put("vehicle-details", detailsService.getVehicleDetails(Integer.parseInt(jsonNode.get("userType").toString()), Integer.parseInt(jsonNode.get("profileId").toString()), Integer.parseInt(jsonNode.get("vehicleId").toString())));
        baseResponse.apiName = "vehicle-Detail";
        baseResponse.status = true;
        baseResponse.data = respnse;
        return ResponseEntity.ok(baseResponse);
    }
}
