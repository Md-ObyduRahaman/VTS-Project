package nex.vts.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetExpenseHeader {
    //,produces = MediaType.APPLICATION_JSON_VALUE

    @GetMapping(value = "/v1/{deviceType}/users/{userId}/{userType}/favourit-vehicles/{limit}/{offset}")
    public ResponseEntity<String> vehicleList(@PathVariable("deviceType") Integer deviceType,
                                              @PathVariable("userId") Integer userId,
                                              @PathVariable("userType") Integer userType,
                                              @PathVariable("limit") Integer limit){




       // return ResponseEntity.ok().body(objectMapper.writeValueAsString(null));
        return  null;


    }


}
