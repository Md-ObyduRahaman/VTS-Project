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
import nex.vts.backend.utilities.PasswordHashUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static nex.vts.backend.controllers.UserLoginController.getCurrentDateTime;
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

    // Request body is as same as login request body just need to change API name
    @PostMapping(value = "/v1/{deviceType}/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refreshToken(@PathVariable("deviceType") Integer deviceType, @RequestParam Map<String, String> requestBody) throws JsonProcessingException {

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }
        reqBody = objectMapper.readValue(requestBody.get("data"), LoginReq.class);
        reqBody.password= PasswordHashUtility.generateSHA256Hash(reqBody.password);

        //TODO: Request Field Validation

        VTS_LOGIN_USER vtsLoginUser = new VTS_LOGIN_USER();
        BaseResponse baseResponse = new BaseResponse();
        ResRefreshToken response = new ResRefreshToken();
        boolean isCredentialMatched = false;

        //Authenticating user
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(reqBody.username);
        if (vtsLoginUserOpt.isPresent()) {
            vtsLoginUser = vtsLoginUserOpt.get();
            if (reqBody.username.equals(vtsLoginUser.getUSERNAME()) && reqBody.password.equals(vtsLoginUser.getPASSWORD()) && vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                isCredentialMatched = true;
                baseResponse.status = true;
                baseResponse.apiName=reqBody.apiName;
                baseResponse.version="V.0.0.1";
            } else if (vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                throw new AppCommonException(4015 + "##Your account is blocked. Please contact with call center");
            } else {
                throw new AppCommonException(4016 + "##User credential not matched");
            }
        } else {
            throw new AppCommonException(4006 + "##User not found");
        }

        if (isCredentialMatched) {
            response.token = jwtService.generateToken(reqBody.username);
            response.serverDateTime = getCurrentDateTime();
        }


        baseResponse.data = response;
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

}
