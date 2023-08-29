package nex.vts.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.Model.AuthRequest;
import nex.vts.backend.Model.Response.BaseResponse;
import nex.vts.backend.Model.Response.LoginResponse;
import nex.vts.backend.Model.User;
import nex.vts.backend.Model.vehicleList.VehicleList;
import nex.vts.backend.Model.vehicleList.VehiclelistItem;
import nex.vts.backend.Repository.UserRepo;
import nex.vts.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
public class UserLoginController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('User')")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{userName}")
    @PreAuthorize("hasAuthority('Admin')")
    public Optional<User> getUsersById(@PathVariable String userName) {
        return userRepo.findByUserName(userName);
    }

    @PostMapping("/new")
    public Integer addNewUser(@RequestBody User userInfo) {
        return userRepo.save(userInfo);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(true);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setLoginSuccess(true);
            loginResponse.setServerDateTime(getCurrentDateTime());
            loginResponse.setToken(jwtService.generateToken(authRequest.getUsername()));
            baseResponse.setData(loginResponse);
            return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));
        } else {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(false);
            baseResponse.setErrorMsg("invalid user request !");
            baseResponse.setErrorCode(999);
            return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

            //throw new UsernameNotFoundException("invalid user request !");
        }


    }

    @GetMapping(value = "/vehicle-list",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<String> getVehicleList() throws JsonProcessingException {
        VehicleList vehicleObject =new VehicleList();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(true);

        ArrayList<VehiclelistItem> vehiclelistItems = new ArrayList<>();
        VehiclelistItem vehiclelistItems1 = new VehiclelistItem("Toyota","Blue","16479","Active", "Z605");
        VehiclelistItem vehiclelistItems2 = new VehiclelistItem("BMW","Black","452466","In-Active", "L457");

        vehiclelistItems.add(vehiclelistItems1);
        vehiclelistItems.add(vehiclelistItems2);

        vehicleObject.setVehiclelist(vehiclelistItems);

        baseResponse.setData(vehicleObject);
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }

    public static String getCurrentDateTime() {
        ZoneId dhaka = ZoneId.of("Asia/Dhaka");
        ZonedDateTime dhakaTime = ZonedDateTime.now(dhaka);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dhakaTime.format(formatter);
    }
    //driver-detals
}
