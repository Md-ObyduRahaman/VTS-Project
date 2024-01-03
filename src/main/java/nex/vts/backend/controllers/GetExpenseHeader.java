package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.GetExpenseHeaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.deObfuscateId;

@RestController
@RequestMapping("/api/private")
public class GetExpenseHeader {
    //,produces = MediaType.APPLICATION_JSON_VALUE

    private final short API_VERSION = 1;

    @Autowired
    GetExpenseHeaderRepo repo;

    @Autowired
    ObjectMapper objectMapper;
    //http://localhost:8009/api/private/v1/1/users/1/1/GetExpenseHeader/860/20120505/20120506

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/GetExpenseHeader/{vehicleId}/{date_from}/{date_to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> vehicleList(@PathVariable("deviceType") Integer deviceType,
                                              @PathVariable("userId") Long userId,
                                              @PathVariable("userType") Integer userType,
                                              @PathVariable("vehicleId") Long vehicleId,
                                              @PathVariable("date_from") String date_from,
                                              @PathVariable("date_to") String date_to) throws JsonProcessingException {



        userId=deObfuscateId(userId);
        vehicleId=deObfuscateId(vehicleId);

        Optional<ArrayList<GetExpansesModel>> GetExpansesList = repo.findAllExpenses(date_from,date_to, Math.toIntExact(vehicleId),deviceType);
        BaseResponse baseResponse = new BaseResponse();


        if (GetExpansesList.isEmpty()) {
            baseResponse.data=new ArrayList<>();
            baseResponse.status = false;
            baseResponse.apiName= "Get Expense Header";
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            GetExpansesListObj getExpansesListObj = new GetExpansesListObj();
            baseResponse.status = true;
            baseResponse.apiName= "Get Expense Header";
            getExpansesListObj.setGetExpansesModels(GetExpansesList);
            baseResponse.data = getExpansesListObj;
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }
    //localhost:8009/api/private//v1/1/users/1006/1/getExpenseList
    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/getExpenseList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> expenseList(@PathVariable("deviceType") Integer deviceType,
                                              @PathVariable("userId") Integer userId,
                                              @PathVariable("userType") Integer userType
                                             ) throws JsonProcessingException {

        userId = Math.toIntExact(deObfuscateId(Long.valueOf(userId)));
        Optional<ArrayList<ExpenseNameList>> GetExpansesNameList = repo.findAllExpensesName(deviceType,userId,userType);
        BaseResponse baseResponse = new BaseResponse();


        if (GetExpansesNameList.isEmpty()) {
            baseResponse.status = false;
            baseResponse.apiName= "Get Expense Header";
            baseResponse.errorMsg = "Data  not found";
            baseResponse.errorCode = 4041;
        } else {
            baseResponse.status = true;
            baseResponse.apiName= "Get Expense Header";
            baseResponse.data = GetExpansesNameList;
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

}



