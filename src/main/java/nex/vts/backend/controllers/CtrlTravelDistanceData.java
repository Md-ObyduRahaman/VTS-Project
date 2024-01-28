package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.MonthTravleDistanceForAll;
import nex.vts.backend.models.responses.TravelDistanceDataModel;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.TravelDistanceDataRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;
import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlTravelDistanceData {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Environment environment;
    private final short API_VERSION = 1;

    @Autowired
    private RepoVtsLoginUser repoVtsLoginUser;
    @Autowired
    TravelDistanceDataRepo travelDistanceDataRepo;
    private final Logger logger = LoggerFactory.getLogger(CtrlTravelDistanceData.class);

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/getTravelDistanceData/{p_all_vehicle_flag}/{vehicleId}/{querymonthYear}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTravelDistanceData( @PathVariable("userId") Long userId,
                                                         @PathVariable("p_all_vehicle_flag") Integer p_all_vehicle_flag,
                                                         @PathVariable("vehicleId") Integer vehicleId,
                                                         @PathVariable("querymonthYear") String querymonthYear,
                                                         @PathVariable("deviceType") Integer deviceType) throws JsonProcessingException, SQLException {
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);
        userId = (long) Math.toIntExact(deObfuscateId(userId));
        vehicleId = Math.toIntExact(deObfuscateId(Long.valueOf(vehicleId)));


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username: " + username);
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUser = repoVtsLoginUser.findByUserName(username,environment.getProperty("application.profiles.shcemaName"));



        TravelDistanceDataModel reqBody=new TravelDistanceDataModel(querymonthYear,"","",vehicleId,p_all_vehicle_flag,0,0,vtsLoginUser.get().getPROFILE_ID(),vtsLoginUser.get().getPROFILE_ID(),vtsLoginUser.get().getUSER_TYPE());

        String date = reqBody.getQuerymonthYear();        /*06-2019*/
        String month = date.substring(0, 2), year = date.substring(2, 6);
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));        /* Calculate the total number of days in the month */
        int totalDays = yearMonth.lengthOfMonth();        /* Find the first date of the month */
        LocalDate firstDate = yearMonth.atDay(1);
        String from_date = String.valueOf(firstDate).replace("-", "");
        reqBody.setP_date_from(Integer.valueOf(from_date));        /* Find the last date of the month */
        LocalDate lastDate = yearMonth.atEndOfMonth();
        String to_date = String.valueOf(lastDate).replace("-", "");
        reqBody.setP_date_to(Integer.valueOf(to_date));
        MonthTravleDistanceForAll monthTravleDistanceForAll = travelDistanceDataRepo.getTravelDistanceData(reqBody, deviceType);
        BaseResponse baseResponse = new BaseResponse();
        if (monthTravleDistanceForAll.getMonthTravleDistancesList().isEmpty()) {
            baseResponse.data=monthTravleDistanceForAll;
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found ";
            baseResponse.errorCode = 4041;
            baseResponse.apiName = "getTravelDistanceData";
        } else {
            baseResponse.status = true;
            baseResponse.data = monthTravleDistanceForAll;
            baseResponse.apiName = "getTravelDistanceData";
        }
        System.out.println(ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse)));

          return  ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
        //return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse),API_VERSION));
    }
}
