package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.NEX_INDIVIDUAL_CLIENT;
import nex.vts.backend.dbentities.NEX_VEHICLE_DEPT;
import nex.vts.backend.dbentities.VTS_EXTENDED_USER_PROFILE;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.LoginResponse;
import nex.vts.backend.repoImpl.*;
import nex.vts.backend.services.JwtService;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import nex.vts.backend.utilities.PasswordHashUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import static nex.vts.backend.controllers.UserLoginController.getCurrentDateTime;
import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlLogin {

    private final Logger logger = LoggerFactory.getLogger(CtrlLogin.class);
    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RepoNexCorporateClient repoNexCorporateClient;
    @Autowired
    private RepoNexIndividualClient repoNexIndividualClient;
    @Autowired
    private RepoNexVehicleDept repoNexVehicleDept;
    @Autowired
    private RepoVtsExtendedUserProfile repoVtsExtendedUserProfile;
    private LoginReq reqBody = null;

    private final short API_VERSION = 1;
    @Autowired
    Environment environment;

    @PostMapping(value = "/v1/{deviceType}/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login( @PathVariable("deviceType") Integer deviceType, @RequestParam Map<String, String> requestBody) throws JsonProcessingException {

        String appActiveProfile = environment.getProperty("spring.profiles.active");

        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(appActiveProfile,deviceType,API_VERSION);

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2##"+deviceType+"##"+API_VERSION);
        }
        reqBody = objectMapper.readValue(aesCrypto.aesDecrypt(requestBody.get("data"),API_VERSION), LoginReq.class);

        reqBody.password=PasswordHashUtility.generateSHA256Hash(reqBody.password);

        //System.out.println("password: " + userDetails.getPassword());


        //TODO: Request Field Validation

        VTS_LOGIN_USER vtsLoginUser = new VTS_LOGIN_USER();
        BaseResponse baseResponse = new BaseResponse();
        LoginResponse loginResponse = new LoginResponse();
        boolean isCredentialMatched = false;


        //Authenticating user
        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(reqBody.username);
        if (vtsLoginUserOpt.isPresent()) {
            vtsLoginUser = vtsLoginUserOpt.get();
            if (reqBody.username.equals(vtsLoginUser.getUSERNAME()) && reqBody.password.equals(vtsLoginUser.getPASSWORD()) && vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                isCredentialMatched = true;
                baseResponse.status = true;
                baseResponse.version="V.0.0.1";
                baseResponse.apiName=reqBody.apiName;
            } else if (vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                throw new AppCommonException(4015 + "##Your account is blocked. Please contact with call center##" + deviceType + "##" + API_VERSION);
            } else {
                throw new AppCommonException(4005 + "##User credential not matched" + deviceType + "##" + API_VERSION);
            }
        } else {
            throw new AppCommonException(4006 + "##User not found" + deviceType + "##" + API_VERSION);
        }

        if (isCredentialMatched) {

            loginResponse.userName = vtsLoginUser.getUSERNAME();
            loginResponse.serverDateTime = getCurrentDateTime();
            loginResponse.token = jwtService.generateToken(reqBody.username);
            loginResponse.loginSuccess = true;
            loginResponse.profileType = vtsLoginUser.getUSER_TYPE();
            loginResponse.profileId = vtsLoginUser.getPROFILE_ID();
            loginResponse.mainAccountId = vtsLoginUser.getMAIN_ACCOUNT_ID();

            switch (vtsLoginUser.getUSER_TYPE()) {

                case 1: {
                    // Nothing to do
                    break;
                }
                case 2:
                    Optional<NEX_VEHICLE_DEPT> nexDeptClientProfileOpt = Optional.empty();
                    try {
                        nexDeptClientProfileOpt = repoNexVehicleDept.getParentProfileIdOfDepartmentClient(vtsLoginUser.getPROFILE_ID());
                    } catch (Exception e) {
                        throw new AppCommonException(4007 + "##Could not fetch profile information" + deviceType + "##" + API_VERSION);
                    }

                    if (nexDeptClientProfileOpt.isPresent()) {
                        NEX_VEHICLE_DEPT nexIndividualClientProfile = nexDeptClientProfileOpt.get();
                        loginResponse.parentId = nexIndividualClientProfile.getPARENT_PROFILE_ID();
                    } else {
                        throw new AppCommonException(4008 + "##Sorry we could not found your profile information" + deviceType + "##" + API_VERSION);
                    }
                    break;
                case 3:
                    Optional<NEX_INDIVIDUAL_CLIENT> nexIndividualClientProfileOpt = Optional.empty();
                    try {
                        nexIndividualClientProfileOpt = repoNexIndividualClient.getParentProfileIdOfIndividualClient(vtsLoginUser.getPROFILE_ID());
                    } catch (Exception e) {
                        throw new AppCommonException(4013 + "##Could not fetch profile information" + deviceType + "##" + API_VERSION);
                    }

                    if (nexIndividualClientProfileOpt.isPresent()) {
                        NEX_INDIVIDUAL_CLIENT nexIndividualClientProfile = nexIndividualClientProfileOpt.get();
                        loginResponse.parentId = nexIndividualClientProfile.getPARENT_PROFILE_ID();
                    } else {
                        throw new AppCommonException(4014 + "##Individual client profile not found" + deviceType + "##" + API_VERSION);
                    }
                    break;
                case 4:
                    Optional<VTS_EXTENDED_USER_PROFILE> nexExtendedClientProfileOpt = Optional.empty();
                    try {
                        nexExtendedClientProfileOpt = repoVtsExtendedUserProfile.getParentProfileIdOfExtendedClient(vtsLoginUser.getPROFILE_ID());
                    } catch (Exception e) {
                        throw new AppCommonException(4012 + "##Could not fetch profile information" + deviceType + "##" + API_VERSION);
                    }

                    if (nexExtendedClientProfileOpt.isPresent()) {
                        VTS_EXTENDED_USER_PROFILE nexExtendedClientProfile = nexExtendedClientProfileOpt.get();
                        loginResponse.parentId = nexExtendedClientProfile.getPARENT_PROFILE_ID();
                    } else {
                        throw new AppCommonException(4010 + "##Individual client profile not found" + deviceType + "##" + API_VERSION);
                    }
                    break;
                default:
                    throw new AppCommonException(4011 + "##Invalid profile type" + deviceType + "##" + API_VERSION);
            }


        }


        baseResponse.data = loginResponse;

        return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse),API_VERSION));

    }



}
