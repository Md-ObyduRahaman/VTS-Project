package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.exceptions.AppCommonException;
import nex.vts.backend.models.requests.LoginReq;
import nex.vts.backend.models.responses.*;
import nex.vts.backend.repositories.GetExpenseHeaderRepo;
import nex.vts.backend.utilities.AESEncryptionDecryption;
import nex.vts.backend.utilities.PasswordHashUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static nex.vts.backend.utilities.UtilityMethods.isNullOrEmpty;

@RestController
@RequestMapping("/api/private")
public class GetExpenseHeader {
    //produces = MediaType.APPLICATION_JSON_VALUE

    private final short API_VERSION = 1;

   @Autowired
    GetExpenseHeaderRepo repo;

   @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Environment environment;
    private LoginReq reqBody = null;
   //http://localhost:8009/api/private/v1/1/users/1/1/GetExpenseHeader/860/20120505/20120506

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/GetExpenseHeader/{vehicleId}/{date_from}/{date_to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> vehicleList(@PathVariable("deviceType") Integer deviceType,
                                              @PathVariable("userId") Integer userId,
                                              @PathVariable("userType") Integer userType,
                                              @PathVariable("vehicleId") Integer vehicleId,
                                              @PathVariable("date_from") String date_from,
                                              @PathVariable("date_to") String date_to,
                                              @RequestParam Map<String, String> requestBody) throws JsonProcessingException {


        String appActiveProfile = environment.getProperty("spring.profiles.active");
        AESEncryptionDecryption aesCrypto = new AESEncryptionDecryption(appActiveProfile,deviceType,API_VERSION);



        Optional<ArrayList<GetExpansesModel>> GetExpansesList = repo.findAllExpenses(date_from,date_to,vehicleId,deviceType);
        BaseResponse baseResponse = new BaseResponse();


        if (GetExpansesList.isEmpty()) {
            baseResponse.status = false;
            baseResponse.apiName= "Get Expense Header";
            baseResponse.version= "v.0.0.1";
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
//i can try to print the object here
      //  System.out.println("mahzabin");
        System.out.println(ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse)));

      return ResponseEntity.ok().body(aesCrypto.aesEncrypt(objectMapper.writeValueAsString(baseResponse),API_VERSION));
    }

    }



