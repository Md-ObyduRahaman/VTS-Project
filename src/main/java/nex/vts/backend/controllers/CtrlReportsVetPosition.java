package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.requests.RptVehPositionReqData;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.ExpenseReportDataList;
import nex.vts.backend.models.responses.LocationInfo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import nex.vts.backend.utilities.PasswordHashUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/private")
public class CtrlReportsVetPosition {
    private RptVehPositionReqData reqBody = null;

    private final short API_VERSION = 1;
    @Autowired
    Environment environment;

    @Autowired
    ObjectMapper objectMapper;
    @GetMapping(value = "/v1/{deviceType}/users/{userId}/expenseReport",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> expenseReport(@RequestHeader("data") String headerValue, @PathVariable("deviceType") Integer deviceType) throws JsonProcessingException {
        System.out.println("headerValue="+headerValue);

        String appActiveProfile = environment.getProperty("spring.profiles.active");
        Integer operatorid = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        String shcemaName = environment.getProperty("application.profiles.shcemaName");

        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(appActiveProfile, deviceType, API_VERSION);

        // Input Validation
        if (headerValue.isEmpty()) {
            throw new AppCommonException(400 + "##BAD REQUEST 2##" + deviceType + "##" + API_VERSION);
        }
        reqBody = objectMapper.readValue(aesCrypto.aesDecrypt(headerValue, API_VERSION), RptVehPositionReqData.class);



        ExpenseReportDataList expenseReportDataList=new ExpenseReportDataList();
        expenseReportDataList.setEngin("on");
        expenseReportDataList.setLocationInfo(new LocationInfo("On Ummoah Qulsum Rd, Bashundhara R/A, Dhaka, 0.063 Km North East From Army Camp Basundhara","9849898","984616"));
        expenseReportDataList.setDateTime("2023-12-05 00:14:37");
        expenseReportDataList.setVehicleName("DMCHA539017");

        BaseResponse baseResponse = new BaseResponse();


        if (expenseReportDataList.getEngin().isEmpty()) {
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.data = expenseReportDataList;
        }

        baseResponse.apiName = "getAccountSummary";
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }
}