package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.AccountSummaryRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;
import static nex.vts.backend.utilities.UtilityMethods.obfuscateId;

@RestController
@RequestMapping("/api/private")
public class CtrlAccountSummary {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountSummaryRepo accountSummaryRepo;
    @Autowired
    Environment environment;

    @Autowired
    RepoVtsLoginUser repoVtsLoginUser;
    private final short API_VERSION = 1;
    private final Logger logger = LoggerFactory.getLogger(CtrlAccountSummary.class);

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/accountSummary/{userType}/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getAccountSummary(@PathVariable("userType") Integer userType, @PathVariable("deviceType") Integer deviceType, @PathVariable("profileId") Integer profileId, @PathVariable("userId") Long userId) throws IOException {
        profileId = Math.toIntExact(deObfuscateId(Long.valueOf(profileId)));
        userId = (long) Math.toIntExact(deObfuscateId(userId));
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        UserFullName fullName;
      try {
           fullName = accountSummaryRepo.getUserFullName(profileId, userType, deviceType).get().get(0);
      }catch (NoSuchElementException e)
      {
          logger.error("Data not found in your array: ", e);
          throw new AppCommonException(4041 + "##Data not found##" + deviceType + "##" + API_VERSION);
      }


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username: " + username);
        VTS_LOGIN_USER loginUser = new VTS_LOGIN_USER();
        Optional<VTS_LOGIN_USER> vtsLoginUser = repoVtsLoginUser.findByUserName(username,environment.getProperty("application.profiles.shcemaName"));
        AccountSummaryInfo summary= getAccountSummary( userType, profileId, vtsLoginUser.get().getMAIN_ACCOUNT_ID(),deviceType,fullName.getFULL_NAME(),fullName.getMOTHER_ACC_NAME());

        BaseResponse baseResponse = new BaseResponse();


        if (summary.getFull_NAME().isEmpty()) {
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            AccountSummaryObj accountSummaryObj = new AccountSummaryObj();
            baseResponse.status = true;
            accountSummaryObj.setAccountSummaries(summary);
            baseResponse.data = accountSummaryObj;
        }

        baseResponse.apiName = "getAccountSummary";
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

    private AccountSummaryInfo getAccountSummary(Integer profileType,Integer profileId,Integer  p_profile_p_id, Integer deviceType,String full_NAME,String motherAccountName) throws IOException {

        Integer runningVehicle= (int) accountSummaryRepo.getVehicleData("RV","running_vehicle",profileType,profileId,p_profile_p_id, getStartOfDay("yyyy-MM-dd HH:mm:ss"), getEndOfDay("yyyy-MM-dd HH:mm:ss"),deviceType,"get_summary_info");
        Integer stoppedVehicle= (int) accountSummaryRepo.getVehicleData("SV","stopped_vehicle",profileType,profileId,p_profile_p_id, getStartOfDay("yyyy-MM-dd HH:mm:ss"), getEndOfDay("yyyy-MM-dd HH:mm:ss"),deviceType,"get_summary_info");
        Integer todaySpeedAlert= (int) accountSummaryRepo.getVehicleData("SPEED","speed_alert",profileType,profileId,p_profile_p_id, getStartOfDay("yyyyMMddHHmmss"), getEndOfDay("yyyyMMddHHmmss"),deviceType,"get_alert_summary");
        Integer todayGEOAlert= (int) accountSummaryRepo.getVehicleData("GEO","geo_alert",profileType,profileId,p_profile_p_id, getStartOfDay("yyyyMMddHHmmss"), getEndOfDay("yyyyMMddHHmmss"),deviceType,"get_alert_summary");
        Integer todayOthersAlert= (int) accountSummaryRepo.getVehicleData("OTHERS","geo_alert",profileType,profileId,p_profile_p_id, getStartOfDay("yyyyMMddHHmmss").substring(0, 8), getEndOfDay("yyyyMMddHHmmss").substring(0, 8),deviceType,"get_alert_summary");
        double todayDistance=accountSummaryRepo.getVehicleData("todayDistance","distance",profileType,profileId,p_profile_p_id, getStartOfDay("yyyy-MM-dd HH:mm:ss"), getEndOfDay("yyyy-MM-dd HH:mm:ss"),deviceType,"get_distance_summary");
        Integer availableSMS= (int) accountSummaryRepo.getVehicleData("AS","running_vehicle",profileType,profileId,p_profile_p_id, getStartOfDay("yyyy-MM-dd HH:mm:ss"), getEndOfDay("yyyy-MM-dd HH:mm:ss"),deviceType,"get_summary_info");
        Integer todayRunningVehicle= (int) accountSummaryRepo.getVehicleData("TRV","running_vehicle",profileType,profileId,p_profile_p_id, getStartOfDay("yyyy-MM-dd HH:mm:ss"), getEndOfDay("yyyy-MM-dd HH:mm:ss"),deviceType,"get_summary_info");


        AccountSummaryInfo accountSummaryInfo=new AccountSummaryInfo();
        accountSummaryInfo.setTotalVehicle(runningVehicle+stoppedVehicle);
        accountSummaryInfo.setTodayAlert(todaySpeedAlert+todayGEOAlert+todayOthersAlert);
        accountSummaryInfo.setTodayDistance(todayDistance);
        accountSummaryInfo.setAvailableSMS(availableSMS);
        accountSummaryInfo.setRunningVehicle(runningVehicle);
        accountSummaryInfo.setStoppedVehicle(stoppedVehicle);
        accountSummaryInfo.setRunningVehicle(todayRunningVehicle);
        accountSummaryInfo.setFull_NAME(full_NAME);
        accountSummaryInfo.setMotherAccountName(motherAccountName);


        //currentDue will Double


        // Starting fetch data
        HttpClient httpClient = HttpClients.createDefault();
        String apiUrl = "https://65641ef1ceac41c0761d748a.mockapi.io/api/v3/ClientDuePaymentForApps/data/1";
        HttpGet httpGet = new HttpGet(apiUrl);
      //  httpGet.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        //End fetching data from API


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode ofClientDue = objectMapper.readTree(responseBody);

        DetailsOfClientDue detailsOfClientDue=new DetailsOfClientDue(
                ofClientDue.get("currentDueLable").toString().replace("\"", "") ,
                ofClientDue.get("currentDue").toString().replace("\"", "") ,
                ofClientDue.get("lastReceivedDate").toString().replace("\"", "") ,
                Integer.parseInt(ofClientDue.get("lastReceivedAmount").toString()), Integer.parseInt(ofClientDue.get("lastBilled").toString())
        );

        accountSummaryInfo.setDetailsOfClientDue(detailsOfClientDue);

        return accountSummaryInfo;
    }


    public static String getStartOfDay(String format) {


        // Current date with time set to 00:00:00
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        String formattedStartOfDay = startOfDay.format(DateTimeFormatter.ofPattern(format));
        System.out.println("Start of the day: " + formattedStartOfDay);
        return formattedStartOfDay;

    }
    public static String getEndOfDay(String format) {


        // Current date with time set to 23:59:59
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        String formattedEndOfDay = endOfDay.format(DateTimeFormatter.ofPattern(format));
        System.out.println("End of the day: " + formattedEndOfDay);
        return formattedEndOfDay;

    }
}
