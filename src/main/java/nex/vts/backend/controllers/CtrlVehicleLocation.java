package nex.vts.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nex.vts.backend.models.responses.BaseResponse;
import nex.vts.backend.services.VehicleLocation_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/v1")
public class CtrlVehicleLocation {

    private Logger logger = LoggerFactory.getLogger(CtrlVehicleLocation.class);
    BaseResponse baseResponse = new BaseResponse();
    private Environment environment;
    private VehicleLocation_Service locationService;

    @Autowired
    public CtrlVehicleLocation(Environment environment, VehicleLocation_Service locationService) {
        this.environment = environment;
        this.locationService = locationService;
    }
    ObjectMapper mapper = new ObjectMapper();


    @GetMapping("/{deviceType}/{lat}/{lon}")
    public ResponseEntity<?> getLatitudeLongitude(@PathVariable(value = "lat")Long lat,
                                                  @PathVariable(value = "lon")Long lon,
                                                  @PathVariable(value = "deviceType") String deviceType){

      return   ResponseEntity.ok(null);
    }
}
