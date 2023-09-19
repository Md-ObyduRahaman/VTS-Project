package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import nex.vts.backend.dbentities.NEX_INDIVIDUAL_CLIENT;
import nex.vts.backend.dbentities.NEX_VEHICLE_DEPT;
import nex.vts.backend.dbentities.VTS_EXTENDED_USER_PROFILE;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.LoginResponse;
import nex.vts.backend.repositories.*;
import nex.vts.backend.services.JwtService;
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

    @PostMapping(value = "/v1/{deviceType}/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login( @PathVariable("deviceType") Integer deviceType, @RequestParam Map<String, String> requestBody) throws JsonProcessingException {

        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2");
        }
        reqBody = objectMapper.readValue(requestBody.get("data"), LoginReq.class);

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
            } else if (vtsLoginUser.getIS_ACCOUNT_ACTIVE() == 1) {
                throw new AppCommonException(400 + "##Your account is blocked. Please contact with call center");
            } else {
                throw new AppCommonException(400 + "##User credential not matched");
            }
        } else {
            throw new AppCommonException(400 + "##User not found");
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
                        throw new AppCommonException(400 + "##Could not fetch profile information");
                    }

                    if (nexDeptClientProfileOpt.isPresent()) {
                        NEX_VEHICLE_DEPT nexIndividualClientProfile = nexDeptClientProfileOpt.get();
                        loginResponse.parentId = nexIndividualClientProfile.getPARENT_PROFILE_ID();
                    } else {
                        throw new AppCommonException(400 + "##Sorry we could not found your profile information");
                    }
                    break;
                case 3:
                    Optional<NEX_INDIVIDUAL_CLIENT> nexIndividualClientProfileOpt = Optional.empty();
                    try {
                        nexIndividualClientProfileOpt = repoNexIndividualClient.getParentProfileIdOfIndividualClient(vtsLoginUser.getPROFILE_ID());
                    } catch (Exception e) {
                        throw new AppCommonException(400 + "##Could not fetch profile information");
                    }

                    if (nexIndividualClientProfileOpt.isPresent()) {
                        NEX_INDIVIDUAL_CLIENT nexIndividualClientProfile = nexIndividualClientProfileOpt.get();
                        loginResponse.parentId = nexIndividualClientProfile.getPARENT_PROFILE_ID();
                    } else {
                        throw new AppCommonException(400 + "##Individual client profile not found");
                    }
                    break;
                case 4:
                    Optional<VTS_EXTENDED_USER_PROFILE> nexExtendedClientProfileOpt = Optional.empty();
                    try {
                        nexExtendedClientProfileOpt = repoVtsExtendedUserProfile.getParentProfileIdOfExtendedClient(vtsLoginUser.getPROFILE_ID());
                    } catch (Exception e) {
                        throw new AppCommonException(400 + "##Could not fetch profile information");
                    }

                    if (nexExtendedClientProfileOpt.isPresent()) {
                        VTS_EXTENDED_USER_PROFILE nexExtendedClientProfile = nexExtendedClientProfileOpt.get();
                        loginResponse.parentId = nexExtendedClientProfile.getPARENT_PROFILE_ID();
                    } else {
                        throw new AppCommonException(400 + "##Individual client profile not found");
                    }
                    break;
                default:
                    throw new AppCommonException(400 + "##Invalid profile type");
            }


        }


        baseResponse.data = loginResponse;

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
        //Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqBody.username, reqBody.password));
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

}
