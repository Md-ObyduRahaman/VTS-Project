package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.ResRefreshToken;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.services.JwtService;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import nex.vts.backend.utilities.PasswordHashUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlRefreshToken {

    private final Logger logger = LoggerFactory.getLogger(CtrlRefreshToken.class);

    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private JwtService jwtService;
    private LoginReq reqBody = null;
    private final short API_VERSION = 1;

    @Autowired
    private Environment environment;

    @PostMapping(value = "/v1/{deviceType}/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refreshToken(@PathVariable("deviceType") Integer deviceType, @RequestParam Map<String, String> requestBody) throws JsonProcessingException {

        String appActiveProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(appActiveProfile, deviceType, API_VERSION);

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##" + "BAD REQUEST 2" + "##" + deviceType + "##" + API_VERSION);
        }
        reqBody = objectMapper.readValue(aesCrypto.aesDecrypt(requestBody.get("data"), API_VERSION), LoginReq.class);
        reqBody.password = PasswordHashUtility.generateSHA256Hash(reqBody.password);

        //TODO: Request Field Validation

        VTS_LOGIN_USER vtsLoginUser = new VTS_LOGIN_USER();
        BaseResponse baseResponse = new BaseResponse();
        ResRefreshToken response = new ResRefreshToken();
        boolean isCredentialMatched = false;

        //Authenticating user
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(reqBody.username,environment.getProperty("application.profiles.shcemaName"));
        if (vtsLoginUserOpt.isPresent()) {
            vtsLoginUser = vtsLoginUserOpt.get();
            if (reqBody.username.equals(vtsLoginUser.getUSERNAME()) && reqBody.password.equals(vtsLoginUser.getPASSWORD()) && vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                isCredentialMatched = true;
                baseResponse.status = true;
                baseResponse.apiName = reqBody.apiName;
                baseResponse.version = "V.0.0.1";
            } else if (vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                throw new AppCommonException(4015 + "##" + "Your account is blocked. Please contact with call center" + "##" + deviceType + "##" + API_VERSION);
            } else {
                throw new AppCommonException(4016 + "##" + "User credential not matched" + "##" + deviceType + "##" + API_VERSION);
            }
        } else {
            throw new AppCommonException(4006 + "##" + "User not found" + "##" + deviceType + "##" + API_VERSION);
        }

        if (isCredentialMatched) {
            response.token = jwtService.generateToken(reqBody.username);
            response.serverDateTime = getCurrentDateTime();
        }


        baseResponse.data = response;
        return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse), API_VERSION));

    }

    public static String getCurrentDateTime() {
        ZoneId dhaka = ZoneId.of("Asia/Dhaka");
        ZonedDateTime dhakaTime = ZonedDateTime.now(dhaka);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dhakaTime.format(formatter);
    }

}
