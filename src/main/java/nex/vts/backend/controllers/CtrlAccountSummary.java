package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.AccountSummaryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class CtrlAccountSummary {

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    AccountSummaryRepo accountSummaryRepo;

    private final Logger logger = LoggerFactory.getLogger(CtrlAccountSummary.class);


    @GetMapping(value = "/v1/{deviceType}/users/{userId}/accountSummary/{userType}/{prfileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getAccountSummary(@PathVariable("userType") Integer userType,
                                                     @PathVariable("prfileId") Integer prfileId) throws JsonProcessingException {

        Optional<ArrayList<AccountSummary>> accountSummaries = accountSummaryRepo.getAccountSummary(prfileId, userType);
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

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

}
