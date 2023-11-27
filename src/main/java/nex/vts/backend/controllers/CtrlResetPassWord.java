package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.ForgotPassWordPayLoadData;
import nex.vts.backend.models.responses.ForgotPassWordResponseData;
import nex.vts.backend.repositories.ResetPasswordRepo;
import nex.vts.backend.services.PasswordValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.sql.SQLException;
import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlResetPassWord {

    @Autowired
    ObjectMapper objectMapper;

   @Autowired
    ResetPasswordRepo resetPasswordRepo;

    @Autowired
    private PasswordValidationService passwordValidationService;



    private ForgotPassWordPayLoadData reqBody = null;

    private final Logger logger = LoggerFactory.getLogger(CtrlResetPassWord.class);


    @PostMapping(value = "/v1/vts/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getResetPassword( @RequestParam Map<String, String> requestBody) throws JsonProcessingException, SQLException {

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }
        reqBody = objectMapper.readValue(requestBody.get("data"), ForgotPassWordPayLoadData.class);

        ForgotPassWordResponseData forgotPassWordResponseData= resetPasswordRepo.getResetPassword(reqBody,"PassChangeVerify");


        ForgotPassWordResponseData forgotPassWordResponseDataOK=new ForgotPassWordResponseData();



        BaseResponse baseResponse = new BaseResponse();


        if (forgotPassWordResponseData.getP_operation_stat()==1) {
            baseResponse.status = true;
            baseResponse.data = resetData(reqBody);
            baseResponse.apiName="resetPassword";

        } else {
            baseResponse.status = false;
            baseResponse.errorMsg = resetData(reqBody).getMessage();
            baseResponse.errorCode = 4042;
            baseResponse.apiName="resetPassword";

        }
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }

    public ForgotPassWordResponseData resetData(ForgotPassWordPayLoadData reqBody) throws SQLException {
        ForgotPassWordResponseData forgotPassWordResponseData=new ForgotPassWordResponseData();
        if (passwordValidationService.isValidPassword(reqBody.getNewPassword())==null) {
            return resetPasswordRepo.getResetPassword(reqBody,"PassChange");
        } else {
            forgotPassWordResponseData.setMessage(passwordValidationService.isValidPassword(reqBody.getNewPassword()));
            return forgotPassWordResponseData;
        }


    }

    }
