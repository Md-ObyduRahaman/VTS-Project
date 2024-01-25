package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.NEX_INDIVIDUAL_CLIENT;
import nex.vts.backend.dbentities.NEX_VEHICLE_DEPT;
import nex.vts.backend.dbentities.VTS_EXTENDED_USER_PROFILE;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.Case1UserInfo;
import nex.vts.backend.models.responses.LoginResponse;
import nex.vts.backend.repoImpl.*;
import nex.vts.backend.repositories.LoginUserInformation;
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

import static nex.vts.backend.utilities.UtilityMethods.getCurrentDateTime;
import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlRefreshToken {
    private final Logger logger = LoggerFactory.getLogger(CtrlLogin.class);
    private final short API_VERSION = 1;
    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    LoginUserInformation loginUserInformation;
    @Autowired
    Environment environment;
    VTS_LOGIN_USER vtsLoginUser = new VTS_LOGIN_USER();
    BaseResponse baseResponse = new BaseResponse();
    LoginResponse loginResponse = new LoginResponse();
    boolean isCredentialMatched = false;
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



    @PostMapping(value = "/v1/{deviceType}/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@PathVariable("deviceType") Integer deviceType, @RequestParam Map<String, String> requestBody) throws JsonProcessingException {

        String appActiveProfile = environment.getProperty("spring.profiles.active");
        Integer operatorid = Integer.valueOf(environment.getProperty("application.profiles.operatorid"));
        String shcemaName = environment.getProperty("application.profiles.shcemaName");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(appActiveProfile, deviceType, API_VERSION); /* TODO Input Validation*/

        if (isNullOrEmpty(requestBody.get("data")))
            throw new AppCommonException(400 + "##BAD REQUEST 2##" + deviceType + "##" + API_VERSION);

        reqBody = objectMapper.readValue(aesCrypto.aesDecrypt(requestBody.get("data"), API_VERSION), LoginReq.class);
        if (operatorid == 1 || operatorid == 8)
            reqBody.password = PasswordHashUtility.generateSHA256Hash(reqBody.password);

        Optional<VTS_LOGIN_USER> vtsLoginUserOpt = repoVtsLoginUser.findByUserName(reqBody.username, reqBody.password);

        if (vtsLoginUserOpt.isPresent()) {
            vtsLoginUser = vtsLoginUserOpt.get();
            if (reqBody.username.equals(vtsLoginUser.getUSERNAME()) && reqBody.password.equals(vtsLoginUser.getPASSWORD()) && vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                isCredentialMatched = true;
                baseResponse.status = true;
                baseResponse.apiName = reqBody.apiName;
            } else if (!reqBody.password.equals(vtsLoginUser.getPASSWORD()))
                throw new AppCommonException(4016 + "##Your password is wrong. Please contact with call center##" + deviceType + "##" + API_VERSION);

            else if (vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 0)
                throw new AppCommonException(4015 + "##Your account is blocked. Please contact with call center##" + deviceType + "##" + API_VERSION);

            else if (vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1)
                throw new AppCommonException(4015 + "##Your account is blocked. Please contact with call center##" + deviceType + "##" + API_VERSION);

            else
                throw new AppCommonException(4005 + "##User credential not matched##" + deviceType + "##" + API_VERSION);

        } else throw new AppCommonException(4006 + "##User not found##" + deviceType + "##" + API_VERSION);

        if (isCredentialMatched) {

            loginResponse.userName = vtsLoginUser.getUSERNAME();
            loginResponse.serverDateTime = getCurrentDateTime();
            loginResponse.token = jwtService.generateToken(reqBody.username);
            loginResponse.loginSuccess = true;
            loginResponse.profileType = vtsLoginUser.getUSER_TYPE();
            loginResponse.profileId = vtsLoginUser.getPROFILE_ID();
            loginResponse.mainAccountId = vtsLoginUser.getMAIN_ACCOUNT_ID();
            loginResponse.operatorid=vtsLoginUser.getOPERATORID();
            loginResponse.customerId=vtsLoginUser.getCUSTOMER_ID();
            String dynamicColumnName;

            if (operatorid == 1 || operatorid == 8) dynamicColumnName = "CORP_PASS";

            else if (operatorid == 2 || operatorid == 3 || operatorid == 7) {/* TODO GP = 1,NEX = 8 ,M2M = 3 ,ROBI = 7*/

                dynamicColumnName = "PASSWORD";
            } else dynamicColumnName = null;
            switch (vtsLoginUser.getUSER_TYPE()) {

                case 1: /*TODO [ Mother-Acc-User ] [ User-Type = 1 ]*/

                    if (vtsLoginUser.getPARENT_PROFILE_ID().equals("0")) { /*TODO validation check from corporate table...?*/

                        Optional<Case1UserInfo> userInfo = loginUserInformation.caseOneAccountInfo(vtsLoginUser.getPROFILE_ID(), vtsLoginUser.getUSERNAME(), reqBody.password, dynamicColumnName, operatorid);
                        loginResponse.profileId = Integer.parseInt(userInfo.get().getID());
                        loginResponse.mainAccountId = Integer.parseInt(userInfo.get().getID());
                    }
                    break;

                case 2: /* TODO [ Child-Acc-User ]  [ User-Type = 2 ]*/
                    Optional<NEX_VEHICLE_DEPT> nexDeptClientProfileOpt = Optional.empty();

                    try {
                        nexDeptClientProfileOpt = repoNexVehicleDept.getParentProfileIdOfDepartmentClient(vtsLoginUser.getUSERNAME(), reqBody.password, operatorid, shcemaName);
                    } catch (Exception e) {
                        throw new AppCommonException(4007 + "##Could not fetch profile information" + deviceType + "##" + API_VERSION);
                    }

                    if (nexDeptClientProfileOpt.isPresent()) {
                        NEX_VEHICLE_DEPT nexIndividualClientProfile = nexDeptClientProfileOpt.get();
                        Integer c2Value = repoNexVehicleDept.getC2(nexIndividualClientProfile.getPARENT_PROFILE_ID(), shcemaName,operatorid);
                        if (c2Value == 1) loginResponse.mainAccountId = nexIndividualClientProfile.getPARENT_PROFILE_ID();
                    } else
                        throw new AppCommonException(4008 + "##Sorry we could not found your profile information" + deviceType + "##" + API_VERSION);

                    break;

                case 3:  /*TODO [ Individual-Acc-User ]  [ User-Type = 3 ]*/
                    if (operatorid == 1 || operatorid == 8) {
                        dynamicColumnName = "IND_PASS";
                    } else if (operatorid == 2 || operatorid == 3 || operatorid == 7) {/* TODO GP = 1,NEX = 8 ,M2M = 3 ,ROBI = 7*/
                        dynamicColumnName = "PASSWORD";
                    } else dynamicColumnName = null;

                    Optional<NEX_INDIVIDUAL_CLIENT> nexIndividualClientProfileOpt = Optional.empty();
                    if (Integer.parseInt(vtsLoginUser.getPARENT_PROFILE_ID()) > 0) {
                        try {
                            nexIndividualClientProfileOpt = repoNexIndividualClient.getParentProfileIdOfIndividualClient(vtsLoginUser.getPROFILE_ID(), vtsLoginUser.getUSERNAME(), reqBody.password, operatorid, shcemaName, dynamicColumnName);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new AppCommonException(4013 + "##Could not fetch profile information##" + deviceType + "##" + API_VERSION);
                        }
                        if (nexIndividualClientProfileOpt.isPresent()) {
                            NEX_INDIVIDUAL_CLIENT nexIndividualClientProfile = nexIndividualClientProfileOpt.get();
                            loginResponse.mainAccountId = nexIndividualClientProfile.getPARENT_PROFILE_ID();
                        } else
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
                        loginResponse.mainAccountId = nexExtendedClientProfile.getPARENT_PROFILE_ID();
                    } else
                        throw new AppCommonException(4010 + "##Individual client profile not found" + deviceType + "##" + API_VERSION);
                    break;
                default:
                    throw new AppCommonException(4011 + "##Invalid profile type" + deviceType + "##" + API_VERSION);
            }
        }
        baseResponse.data = loginResponse;
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
    }
}
