package nex.vts.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehiclePositionReportData;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import nex.vts.backend.repositories.VehiclePositionRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;
import static nex.vts.backend.utilities.VehPaginationLib.is_Valid_Limit;
import static nex.vts.backend.utilities.VehPaginationLib.is_Valid_OffSet;

@RestController
@RequestMapping("/api/private")
public class CtrlVehiclePositionReport {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Environment environment;

    @Autowired
    VehiclePositionRepo vehiclePositionRepo;
    private final short API_VERSION = 1;
    private final Logger logger = LoggerFactory.getLogger(CtrlVehiclePositionReport.class);

    //v1/1/users/13365/1/vehiclePositionReport/25819/20240110/20240117/1
    //v1/1/users/35449794/1/vehiclePositionReport/25819/20240110/20240117/1
    ///api/private/v1/1/3/users/45532698/4035/vehiclePositionReport/0/20240119/20240129/2
    @GetMapping(value = "v1/{deviceType}/{userType}/users/{userId}/{p_userId}/vehiclePositionReport/{vehicleId}/{fromDate}/{toDate}/{locationStat}/{offSet}/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getVehiclePositionReport(@PathVariable("vehicleId") Integer vehicleId,
                                                            @PathVariable("deviceType") Integer deviceType,
                                                            @PathVariable("offSet") Integer offSet,
                                                            @PathVariable("limit") Integer limit,
                                                            @PathVariable("userType") Integer userType,
                                                            @PathVariable("p_userId") String p_userId,
                                                            @PathVariable("fromDate") String fromDate,
                                                            @PathVariable("toDate") String toDate,
                                                            @PathVariable("locationStat") String locationStat,
                                                            @PathVariable("userId") Long userId) throws IOException {
        // profileId = Math.toIntExact(deObfuscateId(Long.valueOf(profileId)));
        userId = (long) Math.toIntExact(deObfuscateId(userId));
        String activeProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(activeProfile, deviceType, API_VERSION);

        if(!is_Valid_Limit(limit))
        {
            throw new AppCommonException(5002 + "##Invalid or incorrect limit!##" + deviceType + "##" + API_VERSION);
        }

        if(!is_Valid_OffSet(offSet))
        {
            throw new AppCommonException(5002 + "##Invalid or incorrect offSet!##" + deviceType + "##" + API_VERSION);
        }

        boolean flag = dateChecker(fromDate, toDate);


        BaseResponse baseResponse = new BaseResponse();

        Optional<ArrayList<VehiclePositionReportData>> vehiclePositionReportData;
        if (flag) {
            vehiclePositionReportData = vehiclePositionRepo.findVehiclePositionRepo(String.valueOf(userId),p_userId, vehicleId, fromDate, toDate, locationStat, deviceType, userType,offSet,limit);
        } else {
            vehiclePositionReportData = Optional.empty();
        }



        if (vehiclePositionReportData.isEmpty()) {
            baseResponse.data = new ArrayList<>();
            baseResponse.status = false;
            baseResponse.apiName="VehiclePositionReport";
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.apiName = "VehiclePositionReport";
            baseResponse.status = true;
            baseResponse.data = vehiclePositionReportData;
        }


        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

    static boolean dateChecker(String fromDateParameter, String toDateParameter) {
        boolean flag = false;
        Date todate = null;
        Date fromdate = null;
        LocalDate currentDate = LocalDate.now();
        Date today = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            fromdate = sdf.parse(fromDateParameter);
            todate = sdf.parse(toDateParameter);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!isSameDay(todate, today) && isWithinThreeMonths(fromdate)) {
            flag = true;
            // System.out.println("Your date is not the current date.");
            // System.out.println("The given date is within the last 3 months.");
        } else {
            flag = false;
            // System.out.println("Your date is the current date.");
            // System.out.println("The given date is more than 3 months ago.");
        }
        return flag;
    }

    // Helper method to check if two dates represent the same day
    private static boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    // Helper method to check if two dates are not more than 3 months apart
    private static boolean isWithinThreeMonths(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar givenCalendar = Calendar.getInstance();
        givenCalendar.setTime(date);

        // Subtract 3 months from the current date
        currentCalendar.add(Calendar.MONTH, -3);

        // Compare the given date with the current date minus 3 months
        return givenCalendar.after(currentCalendar);
    }
}
