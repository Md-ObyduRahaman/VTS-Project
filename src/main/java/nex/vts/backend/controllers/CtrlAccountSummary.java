package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.AccountSummary;
import nex.vts.backend.models.responses.AccountSummaryObj;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.repositories.AccountSummaryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class CtrlAccountSummary {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountSummaryRepo accountSummaryRepo;
    @Autowired
    Environment environment;
    private final short API_VERSION = 1;
    private final Logger logger = LoggerFactory.getLogger(CtrlAccountSummary.class);

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/accountSummary/{userType}/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getAccountSummary(@PathVariable("userType") Integer userType, @PathVariable("deviceType") Integer deviceType, @PathVariable("profileId") Integer profileId, @PathVariable("userId") Long userId) throws JsonProcessingException {
        Long getUserId = deObfuscateId(userId);
        Optional<ArrayList<AccountSummary>> accountSummaries = accountSummaryRepo.getAccountSummary(profileId, userType, deviceType);
        BaseResponse baseResponse = new BaseResponse();
        if (accountSummaries.isEmpty()) {
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            AccountSummaryObj accountSummaryObj = new AccountSummaryObj();
            baseResponse.status = true;
            accountSummaryObj.setAccountSummaries(accountSummaries);
            baseResponse.data = accountSummaryObj;
        }
        baseResponse.version = "V.0.0.1";
        baseResponse.apiName = "getAccountSummary";
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }
}
