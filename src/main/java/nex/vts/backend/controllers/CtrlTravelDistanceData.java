package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.MonthTravleDistanceForAll;
import nex.vts.backend.models.responses.TravelDistanceDataModel;
import nex.vts.backend.repositories.TravelDistanceDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class CtrlTravelDistanceData {

    private TravelDistanceDataModel reqBody = null;

    @Autowired
    ObjectMapper objectMapper;

    private final short API_VERSION = 1;

    @Autowired
    TravelDistanceDataRepo travelDistanceDataRepo;

    private final Logger logger = LoggerFactory.getLogger(CtrlTravelDistanceData.class);


    @GetMapping(value = "/v1/{deviceType}/users/{userId}/getTravelDistanceData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTravelDistanceData(@RequestParam Map<String, String> requestBody, @PathVariable("userId") Integer userId,@PathVariable("deviceType") Integer deviceType) throws JsonProcessingException, SQLException {
        // Input Validation
        if (isNullOrEmpty(requestBody.get("data"))) {
            throw new AppCommonException(400 + "##BAD REQUEST 2##"+deviceType+"##"+API_VERSION);
        }

           reqBody = objectMapper.readValue(requestBody.get("data"), TravelDistanceDataModel.class);
        String date=reqBody.getQuerymonthYear();
        //06-2019
        String month = date.substring(0, 2);
        String year = date.substring(3, 7);

        YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));


        // Calculate the total number of days in the month
        int totalDays = yearMonth.lengthOfMonth();

        // Find the first date of the month
        LocalDate firstDate = yearMonth.atDay(1);
        String  from_date= String.valueOf(firstDate).replace("-", "");
        reqBody.setP_date_from(Integer.valueOf(from_date));

        // Find the last date of the month
        LocalDate lastDate = yearMonth.atEndOfMonth();
        String  to_date= String.valueOf(lastDate).replace("-", "");
        reqBody.setP_date_to(Integer.valueOf(to_date));

        MonthTravleDistanceForAll monthTravleDistanceForAll= travelDistanceDataRepo.getTravelDistanceData(reqBody,deviceType);

        BaseResponse baseResponse = new BaseResponse();


        if (monthTravleDistanceForAll.getMonthTravleDistancesList().isEmpty()) {
            baseResponse.status = false;
            baseResponse.errorMsg = "Data  not found within this time limit";
            baseResponse.errorCode = 4041;
            baseResponse.version="V.0.0.1";
            baseResponse.apiName="getTravelDistanceData";
        } else {
            baseResponse.status = true;
            baseResponse.data = monthTravleDistanceForAll;
            baseResponse.version="V.0.0.1";
            baseResponse.apiName="getTravelDistanceData";
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));


    }

    }
