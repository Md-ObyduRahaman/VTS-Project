package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.GetExpenseHeaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class GetExpenseHeader {
    //,produces = MediaType.APPLICATION_JSON_VALUE


   @Autowired
    GetExpenseHeaderRepo repo;

   @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/GetExpenseHeader/{vehicleId}/{date_from}/{date_to}")
    public ResponseEntity<String> vehicleList(@PathVariable("deviceType") Integer deviceType,
                                              @PathVariable("userId") Integer userId,
                                              @PathVariable("userType") Integer userType,
                                              @PathVariable("vehicleId") Integer vehicleId,
                                              @PathVariable("date_from") String date_from,
                                              @PathVariable("date_to") String date_to) throws JsonProcessingException {








        Optional<ArrayList<GetExpansesModel>> GetExpansesList = repo.findAllExpenses(date_from,date_to,vehicleId);
        BaseResponse baseResponse = new BaseResponse();


        if (GetExpansesList.isEmpty()) {
            baseResponse.status = false;
          //  baseResponse.apiName= "Get Expense Header";
          //  baseResponse.version= "01";
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            GetExpansesListObj getExpansesListObj = new GetExpansesListObj();
            baseResponse.status = true;
            baseResponse.apiName= "Get Expense Header";
            baseResponse.version= "v.0.0.1";
            getExpansesListObj.setGetExpansesModels(GetExpansesList);
            baseResponse.data = getExpansesListObj;
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

    }



