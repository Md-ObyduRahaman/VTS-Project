package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.ForgotPassWordPayLoadData;
import nex.vts.backend.models.responses.ForgotPassWordResponseData;
import nex.vts.backend.repositories.ForgotPasswordRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlForgotPassWord {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;

    private ForgotPassWordPayLoadData reqBody = null;

    private final Logger logger = LoggerFactory.getLogger(CtrlForgotPassWord.class);

    @PostMapping(value = "/v1/vts/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getForgotPassword(@RequestParam Map<String, String> requestBody) throws JsonProcessingException, SQLException {

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }
        reqBody = objectMapper.readValue(requestBody.get("data"), ForgotPassWordPayLoadData.class);

        ForgotPassWordResponseData forgotPassWordResponseData= forgotPasswordRepo.getForgotPassword(reqBody);

        BaseResponse baseResponse = new BaseResponse();


        if (forgotPassWordResponseData.getToken()==0) {
            baseResponse.status = false;
            baseResponse.errorMsg = forgotPassWordResponseData.getMessage();
            baseResponse.errorCode = 4042;
            baseResponse.apiName="forgotPassword";
        } else {
            baseResponse.status = true;
            baseResponse.data = forgotPassWordResponseData;
            baseResponse.apiName="forgotPassword";
        }
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }


    }
