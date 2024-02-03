package nex.vts.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.AccountSummaryRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static nex.vts.backend.utilities.ExtractLocationLib.get_Location;
import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class VehRevGeocoderController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Environment environment;

    private final short API_VERSION = 1;
    private final Logger logger = LoggerFactory.getLogger(VehRevGeocoderController.class);

    @GetMapping(value = "/v1/{deviceType}/{veh_id}/rev_geocoder/location/{lat}/{lon}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getLocation(@PathVariable("veh_id") Integer veh_id, @PathVariable("deviceType") Integer deviceType, @PathVariable("lat") String lat, @PathVariable("lon") String lon) throws IOException {

        BaseResponse baseResponse = new BaseResponse();


        String area=get_Location(lat,lon);


        if (area.isBlank()) {
            baseResponse.data=new ArrayList<>();
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.data = area;
        }

        baseResponse.apiName="VehRevGeocoder";
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }
}
