package nex.vts.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.User;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.models.responses.VehicleList;
import nex.vts.backend.models.responses.VehiclelistItem;
import nex.vts.backend.repositories.DriverRepo;
import nex.vts.backend.repositories.UserRepo;
import nex.vts.backend.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
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
    ObjectMapper objectMapper;
    @Autowired
    DriverRepo driverRepo;
    Integer count = 0;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    public static String getCurrentDateTime() {
        ZoneId dhaka = ZoneId.of("Asia/Dhaka");
        ZonedDateTime dhakaTime = ZonedDateTime.now(dhaka);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dhakaTime.format(formatter);
    }


    /*@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(true);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.loginSuccess = true;
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


    }*/

    /*@PostMapping(value = "/RefreshToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> RefreshAuthAndGetToken(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        Authentication RefreshAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (RefreshAuthentication.isAuthenticated()) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(true);
            RefreshLogin loginResponse = new RefreshLogin();
            loginResponse.setRefreshSuccess(true);
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


    }*/

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

    /*@GetMapping(value = "users/{user_id}/vehicle/{vehicle_id}/driver_info", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<String> getDriverList(@PathVariable("user_id") String user_id, @PathVariable("vehicle_id") String vehicle_id) throws JsonProcessingException {


        DriverData driverData = new DriverData();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(true);

        //  System.out.println("Encoded Data :" + obfuscatePartnerId(user_id));
        System.out.println("decoded Data :" + deObfuscatePartnerId(Long.parseLong(user_id)));

        //DriverDetails driverDetails = new DriverDetails(1234L, "Saruf", "01783726998", "452466", "kolaBagan,Dhaka", "Maruf");
        Optional<DriverDetails> driverDetails = driverRepo.findById(1235L);
        driverData.setDriverList(driverDetails.get());
        baseResponse.setData(driverData);

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }*/

    @PostMapping("/new")
    @Retryable(retryFor = {SQLException.class, IOException.class, ConnectException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2))
    public Integer addNewUser(@RequestBody User userInfo) {
        System.out.println("retry: " + count++);
        return userRepo.save(userInfo);
    }

    @GetMapping(value = "/vehicle-list", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAuthority('User')")
    public ResponseEntity<String> getVehicleList() throws JsonProcessingException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username: " + username);
        System.out.println("password: " + userDetails.getPassword());

        VehicleList vehicleObject = new VehicleList();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.status = true;

        ArrayList<VehiclelistItem> vehiclelistItems = new ArrayList<>();
        VehiclelistItem vehiclelistItems1 = new VehiclelistItem("Toyota", "Blue", "16479", "Active", "Z605");
        VehiclelistItem vehiclelistItems2 = new VehiclelistItem("BMW", "Black", "452466", "In-Active", "L457");

        vehiclelistItems.add(vehiclelistItems1);
        vehiclelistItems.add(vehiclelistItems2);

        vehicleObject.setVehiclelist(vehiclelistItems);

        baseResponse.data = vehicleObject;
        return ResponseEntity.ok().body(objectMapper.writeValueAsString(baseResponse));

    }


}
